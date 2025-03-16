<template>
  <v-container>
    <v-row>
      <v-col cols="12">
        <h1 class="text ml-1">System Logs</h1>

        <br>

        <!-- Filters -->
        <v-row class="mb-4">
          <v-col cols="12" md="4">
            <v-select
              v-model="selectedType"
              :items="logTypes"
              label="Filter by action"
              clearable
            ></v-select>
          </v-col>
          <v-col cols="12" md="4">
            <v-menu v-model="dateMenu" :close-on-content-click="false" transition="scale-transition">
              <template v-slot:activator="{ props }">
                <v-text-field
                  v-model="formattedDate"
                  label="Filter by date"
                  readonly
                  v-bind="props"
                  clearable
                  @click:clear="selectedDate = null"
                ></v-text-field>
              </template>
              <v-date-picker v-model="selectedDate" @update:modelValue="dateMenu = false"></v-date-picker>
            </v-menu>
          </v-col>
          <v-col cols="12" md="4" class="text-right">
            <v-btn color="primary" @click="exportCSV">Export CSV</v-btn>
          </v-col>
        </v-row>

        <!-- Logs table -->
        <v-data-table
          v-model:items-per-page="itemsPerPage"
          :headers="headers"
          :items="filteredLogs"
          :items-per-page-options="[5, 10, 20]"
          class="text-left"
        >
          <template v-slot:item.timestamp="{ item }">
            {{ new Date(item.timestamp).toLocaleString() }}
          </template>
          <template v-slot:item.type="{ item }">
            {{ formatLogType(item.type) }}
          </template>
          <template v-slot:item.details="{ item }">
            <span v-html="item.details.replace(/\n/g, '<br>')"></span>
          </template>
        </v-data-table>
      </v-col>
    </v-row>
  </v-container>
</template>

<script setup>
import { ref, computed, onMounted } from "vue";
import RemoteServices from "@/services/RemoteService";

const logs = ref([]);
const search = ref("");
const selectedDate = ref(null);
const selectedType = ref(null);
const dateMenu = ref(false);
const itemsPerPage = ref(10);

const logTypes = ref([
  { title: "Criação de Pessoa", value: "CREATE_PERSON" },
  { title: "Atualização de Pessoa", value: "EDIT_PERSON" },
  { title: "Remoção de Pessoa", value: "DELETE_PERSON" },
  { title: "Alteração do Workflow de Tese", value: "UPDATE_THESIS_WORKFLOW" },
  { title: "Alteração do Workflow de Defesa", value: "UPDATE_DEFENSE_WORKFLOW" }
]);

const headers = [
  { title: "Timestamp", key: "timestamp" },
  { title: "Action", key: "type" },
  { title: "Details", key: "details" }
];

const fetchLogs = async () => {
  try {
    logs.value = await RemoteServices.getLogs();
  } catch (error) {
    console.error("Error fetching logs:", error);
  }
};

onMounted(fetchLogs);

const formattedDate = computed(() => {
  return selectedDate.value ? new Date(selectedDate.value).toLocaleDateString() : "";
});

const formatLogType = (type) => {
  const typeMap = {
    CREATE_PERSON: "Criação de Pessoa",
    EDIT_PERSON: "Atualização de Pessoa",
    DELETE_PERSON: "Remoção de Pessoa",
    UPDATE_THESIS_WORKFLOW: "Alteração do Workflow de Tese",
    UPDATE_DEFENSE_WORKFLOW: "Alteração do Workflow de Defesa"
  };
  return typeMap[type] || type;
};

const filteredLogs = computed(() => {
  return logs.value.filter(log => {
    const matchesSearch = selectedType.value ? log.type === selectedType.value : true;
    const matchesDate = selectedDate.value
      ? new Date(log.timestamp).toDateString() === new Date(selectedDate.value).toDateString()
      : true;
    return matchesSearch && matchesDate;
  });
});

const exportCSV = () => {
  const csvContent = [
    ["Timestamp", "Action", "Details"],
    ...logs.value.map(log => [
      new Date(log.timestamp).toLocaleString(),
      formatLogType(log.type),
      log.details.replace(/\n/g, " | ")
    ])
  ]
    .map(row => row.join(","))
    .join("\n");

  const blob = new Blob([csvContent], { type: "text/csv;charset=utf-8;" });
  const link = document.createElement("a");
  link.href = URL.createObjectURL(blob);
  link.setAttribute("download", "system_logs.csv");
  document.body.appendChild(link);
  link.click();
  document.body.removeChild(link);
};
</script>
