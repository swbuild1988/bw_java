<template>
	<div>
		<div>
			<!-- <div class="arrow">
			<p>-->
			<!-- 上一页 -->
			<!-- <span @click="changePdfPage(0)" class="turn" :class="{grey: currentPage==1}">上一页</span>
			{{currentPage}} / {{pageCount}}-->
			<!-- 下一页 -->
			<!-- <span @click="changePdfPage(1)" class="turn" :class="{grey: currentPage==pageCount}">下一页</span>
                    <span class="downBtn">下载</span>
                </p>
			</div>-->
			<div class="PDFBox" style="height: 75vh;width: 61vw;">
				<!-- <pdf
                    :src="src"
                    :page="currentPage"
                    @num-pages="pageCount=$event"
                    @page-loaded="currentPage=$event"
                    @loaded="loadPdfHandler"
                >
				</pdf>-->
				<pdf :src="src" style="width: 100%;"></pdf>
			</div>
		</div>
	</div>
</template>
<script>
import pdf from "vue-pdf";
export default {
	components: { pdf },
	data() {
		return {
			// currentPage: 0, // pdf文件页码
			// pageCount: 0, // pdf文件总页数
			// fileType: 'pdf', // 文件类型
			src: ""
			// numPages: 0
		};
	},
	methods: {
		//预览pdf
		previewPDF(url) {
			this.src = pdf.createLoadingTask({ url: url });
		},
		//下载PDF
		downloadPDF(url, fileName) {
			this.axios({
				method: "get",
				responseType: "arraybuffer",
				url: url
			})
				.then(
					function(res) {
						this.fileDownload(res.data, fileName);
					}.bind(this)
				)
				.catch(
					function(error) {
						this.$Message.error("下载出错" + error);
					}.bind(this)
				);
		},
		fileDownload: function(data, fileName) {
			let blob = new Blob([data], {
				// type: "application/octet-stream"
				type: "application/vnd.ms-excel"
			});
			let filename = fileName || "报表.xls";
			if (typeof window.navigator.msSaveBlob !== "undefined") {
				window.navigator.msSaveBlob(blob, filename);
			} else {
				var blobURL = window.URL.createObjectURL(blob);
				var tempLink = document.createElement("a");
				tempLink.style.display = "none";
				tempLink.href = blobURL;
				tempLink.setAttribute("download", filename);
				if (typeof tempLink.download === "undefined") {
					tempLink.setAttribute("target", "_blank");
				}
				document.body.appendChild(tempLink);
				tempLink.click();
				document.body.removeChild(tempLink);
				window.URL.revokeObjectURL(blobURL);
			}
		}
		// 改变PDF页码,val传过来区分上一页下一页的值,0上一页,1下一页
		// changePdfPage (val) {
		//     if (val === 0 && this.currentPage > 1) {
		//         this.currentPage--
		//     }
		//     if (val === 1 && this.currentPage < this.pageCount) {
		//         this.currentPage++
		//     }
		// },
		// pdf加载时
		// loadPdfHandler (e) {
		//     this.currentPage = 1 // 加载的时候先加载第一页
		// },
	}
};
</script>
<style scoped>
.PDFBox{
	overflow-y: auto;
	overflow-x: hidden;
}
.PDFBox::-webkit-scrollbar{
    width: 0.4vmin;
    height: 0.4vmin;
}
.PDFBox::-webkit-scrollbar-thumb{
    border-radius: 0.5vmin;
    -webkit-box-shadow: inset 0 0 5px rgba(228, 198, 198, 0.2);
    background: rgba(0, 0, 0, 0.2)
}
.PDFBox::-webkit-scrollbar-track{
    border-radius: 0;
    -webkit-box-shadow: inset 0 0 5px rgba(221, 208, 208, 0.2);
    background: rgba(0, 0, 0, 0.1)
}
</style>
