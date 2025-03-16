<template>
  <v-container class="fill-height d-flex flex-column justify-center align-center text-center">
    <v-card class="elevation-12 pa-6 rounded-lg text-center bg-white" max-width="800">
      <v-card-title class="text-h4 font-weight-bold text-primary">
        🎓 PhD Management System
      </v-card-title>
      <v-card-subtitle class="text-body-1 mt-2 text-grey-darken-1">
        Uma plataforma eficiente para acompanhar o progresso de teses e defesas académicas
      </v-card-subtitle>
      <v-card-text class="mt-4">
        <p class="text-body-1">
          Organiza e controla facilmente o estado das teses, visualiza estatísticas detalhadas e gerencia todo o fluxo de trabalho académico em um só lugar
        </p>
      </v-card-text>
      <v-divider class="my-4"></v-divider>
      <v-row class="mt-2" justify="center" align="center">
        <v-col cols="12" class="mb-1">
          <v-btn color="grey-darken-1" to="/people" block>
            👥 Gestão de Pessoas
          </v-btn>
        </v-col>
        <v-col cols="12" class="mb-1">
          <v-btn color="primary" to="/students" block>
            📋 Listagem de Alunos
          </v-btn>
        </v-col>
        <v-col cols="12" class="mb-1">
          <v-btn color="purple" to="/statistics" block>
            📊 Estatísticas
          </v-btn>
        </v-col>
      </v-row>
    </v-card>
  </v-container>
</template>

<script setup lang="ts">
</script>

<style scoped>
.fill-height {
  min-height: 100vh;
  background: radial-gradient(circle, #ffffff, #2196F3);
  display: flex;
  align-items: center;
  justify-content: center;
}
.bg-white {
  background-color: white;
}
.text-primary {
  color: #0D47A1;
}
.text-grey-darken-1 {
  color: #607D8B;
}
.v-btn {
  font-size: 1rem;
  font-weight: bold;
}
.v-card-subtitle {
  white-space: normal;
  word-wrap: break-word;
}
</style>
