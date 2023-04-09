<script setup>
defineProps({
    data: Array,
    columnLabels: Array
});
</script>

<template>
    <table>
        <thead>
            <tr>
                <th scope="col" v-for="label in columnLabels">{{ label }}</th>
                <th scope="col" v-if="!!$slots.end">Aktionen</th>
            </tr>
        </thead>
        <tbody>
            <tr v-for="row in data">
                <td v-for="entry in row">{{ entry }}</td>
                <td v-if="!!$slots.end">
                    <slot :item="row" name="end"></slot>
                </td>
            </tr>
        </tbody>
    </table>
</template>