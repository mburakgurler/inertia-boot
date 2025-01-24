<template>
  <div>
    <nav>
      <Link href="/home">Home</Link>
      <Link href="/about">About</Link>
    </nav>

    <main>
      <slot></slot>
    </main>
  </div>
</template>

<script lang="ts" setup>
import { Link } from '@inertiajs/vue3'
</script>