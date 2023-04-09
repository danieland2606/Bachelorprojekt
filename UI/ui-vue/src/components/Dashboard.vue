<script setup>
import { reactive, ref, computed } from 'vue';
import { formatAddress } from '../util.js';
import SimpleTable from './SimpleTable.vue';

let customerList = ref(null);

fetch('http://localhost:8000/customers')
    .then(response => response.json())
    .then(customers => { customerList.value = customers.customerList });

const columnData = computed(() =>
    customerList.value?.map(customer =>
        [customer.id, customer.firstName, customer.lastName, formatAddress(customer.address)])
);

const tableProps = reactive({
    columnLabels: ['ID', 'Vorname', 'Nachname', 'Adresse'],
    data: columnData
});
</script>

<template>
    <main>
        <h1>Mitarbeiter Dashboard</h1>
        <input type="text" placeholder="Suche.." hidden />
        <button type="button">Neuer Kunde</button>
        <SimpleTable v-bind="tableProps">
            <template #end="{ item }">
                <button type="button">details</button>
            </template>
        </SimpleTable>
    </main>
</template>