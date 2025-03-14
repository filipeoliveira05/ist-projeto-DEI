<template>
  <v-dialog v-model="dialog" max-width="400">
    <template v-slot:activator="{ props: activatorProps }">
      <v-icon v-bind="activatorProps" @click="openDialog">mdi-pencil</v-icon>
    </template>

    <v-card title="Editar Pessoa">
      <v-card-text>
        <v-text-field label="Nome" v-model="editedPerson.name"></v-text-field>
        <v-text-field label="IST ID" v-model="editedPerson.istId"></v-text-field>
        <v-select :items="['Coordenador', 'Staff', 'Aluno', 'Professor', 'SC']"
                  label="Categoria" v-model="editedPerson.type"></v-select>
        <v-text-field label="Email" v-model="editedPerson.email"></v-text-field>
        <v-switch label="Ativo" v-model="editedPerson.status"></v-switch>
        <v-text-field label="Telefone" v-model="editedPerson.phoneNumber"></v-text-field>
      </v-card-text>

      <v-divider></v-divider>

      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn text="Cancelar" @click="dialog = false"></v-btn>
        <v-btn color="primary" text="Salvar" @click="confirmSave"></v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>

  <v-dialog v-model="confirmDialog" max-width="400">
    <v-card>
      <v-card-title class="headline">Confirmar Alterações</v-card-title>
      <v-card-text>
        Tem certeza que deseja salvar as alterações?
      </v-card-text>

      <v-divider></v-divider>

      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn text="Não" @click="confirmDialog = false"></v-btn>
        <v-btn color="green" text="Sim" @click="updatePerson"></v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script setup lang="ts">
import { ref, defineProps, defineEmits } from 'vue'
import RemoteService from '../../services/RemoteService'
import type PersonDto from '../../models/PersonDto'

const dialog = ref(false)
const confirmDialog = ref(false)
const editedPerson = ref<PersonDto>({} as PersonDto)

const props = defineProps<{ person: PersonDto }>()
const emit = defineEmits(['person-updated'])

const typeMappings = {
  "Aluno": "STUDENT",
  "Professor": "TEACHER",
  "Coordenador": "COORDINATOR",
  "Staff": "STAFF",
  "SC": "SC"
}

const openDialog = () => {
  editedPerson.value = { ...props.person };

  editedPerson.value.type = Object.keys(typeMappings).find(
    key => typeMappings[key as keyof typeof typeMappings] === props.person.type
  ) || '';

  dialog.value = true;
};

const confirmSave = () => {
  confirmDialog.value = true;
}

const updatePerson = async () => {
  try {
    const personToSend = {
      ...editedPerson.value,
      type: typeMappings[editedPerson.value.type as keyof typeof typeMappings]
    };

    await RemoteService.updatePerson(personToSend);

    emit('person-updated');
    dialog.value = false;
    confirmDialog.value = false;
  } catch (error) {
    console.error("Errr updating person:", error);
  }
};
</script>
