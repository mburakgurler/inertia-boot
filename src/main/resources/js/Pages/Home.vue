<template>
  <Layout>
    <h1>{{ title }}</h1>
    <div v-if="user">
      <p>Welcome, {{ user.name }}!</p>
      <p>Email: {{ user.email }}</p>
    </div>
  </Layout>
</template>

<script lang="ts" setup>
import Layout from "@/components/Layout.vue";
defineProps<{
  title: string
  user: {
    id: number
    name: string
    email: string
  }
}>()
</script>