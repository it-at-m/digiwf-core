<template>
  <div>
    <ul>
      <li><a :href="`/${type}/${artifact}`" target="_blank">{{$props.artifact}}</a></li>
    </ul>
  </div>
</template>

<script>
export default {
  props: {
    artifact: String,
    type: String,
  }
}
</script>
