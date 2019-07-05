<template>
	<div class="dataBoxModul" :style="{ width: boxWidth+'%',height: boxHeight+'vh' }">
		<div class="backBox">
			<img :src=imgSrc style="width: 10.5vmin;height: 10.5vmin"/>
		</div>
		<div class="showData" :style="{ color: showDataColor }">{{label}}:{{value}}</div>
	</div>
</template>

<script>
	export default {
		name:'dataBox',
		props:{
			boxWidth:{
				default: 100
			},
			boxHeight:{
				default: 28
			},
			value: {
				default: 0.0
			},
			label: {
				default:"label"
			},
			imgSrc:{
				default:"./logo.png"
			},
			imgBac:{
				default: '#fff'
			},
			imgBorderLeftTopRadius:{
				default: 10
			},
			imgBorderRightTopRadius:{
				default: 10
			},
			valColor:{
				default: '#fff'
			},
			labelColor:{
				default: '#fff'
			},
			showDataColor: {
				default: '#fff'
			}
		},
		methods(){
			this.axios.get(this.dataSrc).then(response=>{
				this.value = response.date
			})
		}
	}
</script>

<style scoped>
	.dataBoxModul{
		border-radius: 4px;
		position: relative;
		background: url("../../assets/UM/boxBG.png") no-repeat;
		background-size: 100% 100%;
	}
	.backBox{
		text-align: center;
		height: 19vh;
		line-height: 30vh;
	}
	.showData{
		text-align: center;
		font-size: 1.5vw;
		z-index: 2;
	}
</style>


