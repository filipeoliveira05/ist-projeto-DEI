<template>
  <v-app-bar color="secondary" :height="36" class="px-2">
    <v-toolbar-items>
      <v-btn href="https://dei.tecnico.ulisboa.pt/" selected-class="no-active" class="dei-title" size="small">
        Departamento de Engenharia Informática
      </v-btn>
      <v-btn href="/" icon>
        <v-icon>mdi-home</v-icon>
      </v-btn>
    </v-toolbar-items>
    <v-spacer />
    <span>Autenticado como: {{ currentRole }}</span>
    <v-spacer />
    <v-toolbar-items class="align-center">
      <DarkModeSwitch />
    </v-toolbar-items>

    <v-toolbar-items class="ms-2">
      <v-btn size="small" @click="changeRole('student')">Aluno</v-btn>
      <v-btn size="small" @click="changeRole('coordinator')">Coordenador</v-btn>
      <v-btn size="small" @click="changeRole('staff')">Staff</v-btn>
      <v-btn size="small" @click="changeRole('admin')">SC</v-btn>
      <v-btn size="small" @click="changeRole('teacher')">Professor</v-btn>
    </v-toolbar-items>
    <v-toolbar-items class="ms-2">
      <v-btn size="small" variant="text">
        Terminar sessão
        <v-icon size="small" class="ms-1" icon="mdi-logout"></v-icon>
      </v-btn>
    </v-toolbar-items>
  </v-app-bar>
</template>

<script setup lang="ts">
import DarkModeSwitch from './DarkModeSwitch.vue'
import { useRoleStore } from '../stores/role'
import { computed } from 'vue'
import { ref, watch } from 'vue'

const roleStore = useRoleStore()

const changeRole = async (role: string) => {
  roleStore.$patch({ currentRole: role })
}

const currentRole = computed(() => roleStore.currentRole)

</script>

<style scoped>
.dei-title:hover {
  background-color: transparent !important;
}
</style>
