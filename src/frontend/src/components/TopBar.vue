<template>
  <UtilBar />
  <NavBar :navbarItems="navbarItems" />
</template>

<script setup lang="ts">
import UtilBar from '../components/UtilBar.vue'
import NavBar from '../components/NavBar.vue'

const navbarItems = [
  { name: 'Pessoal', path: '/people', icon: 'mdi-account-group' },
  { name: 'Alunos', path: '/students', icon: 'mdi-account-multiple' },
  { name: 'Estatísticas', path: '/statistics', icon: 'mdi-chart-bar' },
  { name: 'Logs', path: '/logs', icon: 'mdi-file-document' }
]
</script>
