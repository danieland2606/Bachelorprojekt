<script setup>
defineProps({ customerList: Array });

</script>

<template>
    <main>
        <h1>Mitarbeiter Dashboard</h1>
        <input type="text" placeholder="Suche.." hidden />
        <button type="button">Neuer Kunde</button>
        <table>
            <thead>
                <tr>
                    <th scope="col">ID</th>
                    <th scope="col">Vorname</th>
                    <th scope="col">Nachname</th>
                    <th scope="col">Adresse</th>
                    <th scope="col">Aktionen</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="customer in customerList">
                    <td>{{ customer.id }}</td>
                    <td>{{ customer.firstName }}</td>
                    <td>{{ customer.lastName }}</td>
                    <td>
                        {{ customer?.address?.street }}, {{ customer?.address?.postalCode }} {{ customer?.address?.city }}
                    </td>
                    <td>
                        <button type="button">details</button>
                    </td>
                </tr>
            </tbody>
        </table>
    </main>
</template>