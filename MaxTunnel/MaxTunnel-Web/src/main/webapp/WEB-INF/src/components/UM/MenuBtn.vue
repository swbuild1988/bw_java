<template>
	<div>
		<Button
			type="info"
			:disabled="btnStatus"
			:class="className"
			@click="goToMoudle({ path:routerUrl})"
		>
			<div>
				<img :src="imgUrl" :width="imgWidth">
				<div :style="{fontSize:mySize}">name</div>
			</div>
		</Button>
	</div>
</template>

<script>
export default {
	name: "menu-btn",
	props: {
		btnStatus: false,
		name: "",
		imgUrl: "",
		routerUrl: "",
		imgWidth: "",
		className: "",
		mySize: ""
	},
	data() {
		return {};
	},
	mounted() {},
	methods: {
		goToMoudle: function(path) {
			if (
				String(this.$router.history.current.fullPath).indexOf(
					String(path.path)
				) < 0
			) {
				if (path.path == "/VMMain") {
					const { href } = this.$router.resolve({
						name: "可视化主页"
					});
					window.open(href, "_blank");
				} else {
					this.$router.push(path);
				}
			}
		}
	}
};
</script>
<style scoped>
</style>
