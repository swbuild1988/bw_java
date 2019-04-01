<template>
	<div>
		<h2 class="title">能耗明细报表</h2>
		<div>
			<div class="dateBox">
				<Tabs v-model="type" @on-click="currentTabPane(type)">
					<TabPane
						v-for="(item,index) in timeContainer"
						:key="index"
						:label="`${item.key}报`"
						:name="`${item.val}`"
					>
						<div class="itemBox" v-for="(item,index) in cycleReport" :key="index">
							<span class="leftBox"></span>
							<span class="itemContent">{{item.name}}</span>
							<div class="btnBox">
								<Button type="primary" size="small" @click="previewCycle(item.id)">预览</Button>
								<Button type="primary" size="small" @click="downloadCycle(item.id,item.name)">下载</Button>
							</div>
						</div>
					</TabPane>
				</Tabs>
			</div>
			<div class="showReport">
				<PDF ref="pdf"></PDF>
			</div>
		</div>
	</div>
</template>
<script>
import PDF from "../../../../../components/UM/MAM/pdfPerviewDownload";
import { DataAnalysisService } from "../../../../../services/DataAnalysis";
import Vue from "vue";
export default {
	components: { PDF },
	data() {
		return {
			cycleReport: [],
			ofType: 2,
			type: 3,
			timeContainer: []
		};
	},
	mounted() {
		this.cycleDown();
		DataAnalysisService.timeEnums().then(res => {
			this.timeContainer = res.slice(2);
		});
	},
	methods: {
		cycleDown() {
			var params = {
				ofType: this.ofType,
				type: this.type
			};
			DataAnalysisService.chooseDown(params).then(res => {
				this.cycleReport = res;
			});
		},
		currentTabPane(value) {
			this.type = value;
			this.cycleDown();
		},
		//预览
		previewCycle(id) {
			let _this = this;
			this.$refs.pdf.previewPDF(_this.ApiUrl + "/reports/preview/" + id);
		},
		//下载
		downloadCycle(id, fileName) {
			let _this = this;
			this.$refs.pdf.downloadPDF(
				_this.ApiUrl + "/reports/download/" + id,
				fileName
			);
		}
	}
};
</script>
<style scoped>
.title {
	font-size: 2.8vmin;
	color: #13278e;
	margin-left: 1vmin;
}
.dateBox,
.showReport {
	padding: 2vmin;
	float: left;
	height: 80vh;
	overflow: auto;
}
.dateBox {
	width: 20%;
}
.showReport {
	width: 80%;
}
.itemBox {
	line-height: 4vmin;
	margin: 0.5vmin;
	background: #0c77b717;
	padding-right: 0.5vmin;
	min-height: 25px;
}
.leftBox,
.itemContent {
	display: inline-block;
	vertical-align: top;
}
.leftBox {
	width: 0.5vmin;
	background: #ed3f14d1;
	height: 4vmin;
}
.itemContent {
	font-size: 1.5vmin;
}
.btnBox {
	float: right;
	width: 33%;
}
@media (min-width: 2200px) {
	.ivu-btn,
	.ivu-btn-small {
		border-radius: 0.5vmin;
		padding: 0.5vmin 0.7vmin;
		font-size: 1.2vmin !important;
	}
}
</style>

