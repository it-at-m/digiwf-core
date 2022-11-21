<template>
  <div>
    <h2>{{ $props.title }}</h2>
    <span v-if="$props.introducedWithVersion"><small>Eingeführt mit Version <Badge :text="$props.introducedWithVersion"/></small></span>
    <p>
      {{ $props.description }}
    </p>
    <p v-if="$props.linkToDocumentation">
      <a :href="$props.linkToDocumentation" >Dokumentation →</a>
    </p>
  </div>
</template>

<script>
export default {
  props: {
    title: String,
    description: String,
    linkToDocumentation: String | undefined,
    introducedWithVersion: String | undefined,
  }
}
</script>
