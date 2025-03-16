<template>
  <v-container fluid>
    <v-row class="mb-6">
      <v-col cols="12">
        <h1 class="text-h3 text-center font-weight-bold">System Logs</h1>
        <br />
        <br />

        <!-- Filters -->
        <v-row class="mb-4" align="center">
          <v-col cols="12" md="4">
            <v-select
              v-model="selectedType"
              :items="logTypes"
              label="Filter by action"
              clearable
              dense
              outlined
              class="filter-select"
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
                  outlined
                  dense
                  class="filter-select"
                ></v-text-field>
              </template>
              <v-date-picker v-model="selectedDate" @update:modelValue="dateMenu = false" @click:date="dateMenu = false" />
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
          class="text-left elevation-1"
          item-value="timestamp"
          item-key="timestamp"
          dense
          hide-default-footer
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

<style scoped>
.filter-select {
  background-color: #f5f5f5;
  border-radius: 8px;
}

.text-h3 {
  color: #000000;
}

.v-btn {
  border-radius: 8px;
  box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);
}

.v-btn:hover {
  background-color: #1976d2;
}

.export-btn {
  border-radius: 0;
  padding: 10px 20px;
  font-size: 16px;
  font-weight: bold;
  text-transform: none;
}

.v-data-table {
  background-color: #ffffff;
  border-radius: 10px;
}

.elevation-1 {
  box-shadow: 0px 2px 4px rgba(0, 0, 0, 0.1);
}

.v-data-table .v-data-table-header {
  background-color: #f1f1f1;
  color: #0d47a1;
  font-weight: bold;
}

.v-data-table .v-data-table__wrapper {
  margin-top: 20px;
}

.v-data-table th, .v-data-table td {
  padding: 10px 15px;
  font-size: 14px;
  text-align: left;
}

.v-data-table tr:nth-child(even) {
  background-color: #f9f9f9;
}

.v-data-table tr:hover {
  background-color: #f1f1f1;
}

.v-text-field, .v-select {
  background-color: #fafafa;
  border-radius: 8px;
}
</style>
