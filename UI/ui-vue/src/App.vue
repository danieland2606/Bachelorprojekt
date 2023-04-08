<script setup>
// This starter template is using Vue 3 <script setup> SFCs
// Check out https://vuejs.org/api/sfc-script-setup.html#script-setup
import { ref } from 'vue'
import HelloWorld from './components/HelloWorld.vue';
import Dashboard from './components/Dashboard.vue';

let customerList = ref(null)

fetch('http://localhost:8000/customers').then(response => response.json()).then(customers => { customerList.value = customers.customerList });
</script>

<template>
  <Dashboard :customer-list="customerList" />
</template>

<style scoped>
.logo {
  height: 6em;
  padding: 1.5em;
  will-change: filter;
}

.logo:hover {
  filter: drop-shadow(0 0 2em #646cffaa);
}

.logo.vue:hover {
  filter: drop-shadow(0 0 2em #42b883aa);
}
</style>
