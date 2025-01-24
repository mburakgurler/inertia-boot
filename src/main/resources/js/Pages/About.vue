<template>
  <Layout>
    <h1>{{ title }}</h1>
    <p>This is the about page.</p>
  </Layout>
</template>

<script lang="ts" setup>
import Layout from "@/components/Layout.vue";

defineProps<{
  title: string
}>()
</script>