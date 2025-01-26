<template>
  <div class="min-h-screen flex items-center justify-center">
    <div class="text-center">
      <h1 class="text-4xl font-bold mb-4">{{ status }}</h1>
      <p class="text-gray-600">{{ message }}</p>
      <Link href="/" class="mt-4 text-indigo-600 hover:text-indigo-800">Back to Home</Link>
    </div>
  </div>
</template>

<script setup lang="ts">
import { Link } from '@inertiajs/vue3'

defineProps<{
  status: number
  message: string
}>()
</script>