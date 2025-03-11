<template>
    <v-dialog v-model="dialog" max-width="400">
      <template v-slot:activator="{ props: activatorProps }">
        <v-icon v-bind="activatorProps" @click="openDialog">mdi-delete</v-icon>
      </template>
  
      <v-card>
        <v-card-title class="headline">Confirmar Exclusão</v-card-title>
        <v-card-text>
          Tem a certeza que deseja apagar "{{ person.name }}"?
        </v-card-text>
  
        <v-divider></v-divider>
  
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn text="Não" @click="dialog = false"></v-btn>
          <v-btn color="red" text="Sim" @click="confirmDelete"></v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </template>
  
  <script setup lang="ts">
  import { ref, defineProps, defineEmits } from 'vue'
  
  const dialog = ref(false)
  const props = defineProps<{ person: any }>()
  const emit = defineEmits(['confirm-delete'])
  
  const openDialog = () => {
    dialog.value = true
  }
  
  const confirmDelete = () => {
    emit('confirm-delete', props.person)
    dialog.value = false
  }
  </script>
  