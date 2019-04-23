<template>
    <Modal v-model="videoInfo.isShowVideo" title="关联视频">
        <Row>
            <CheckboxGroup v-model="selectdVideo">
                <Col span="12" v-for="item in curVideo" :key="item.id">
                    <div class="videoBox">
                        <videoComponent v-bind:video="item" v-bind:id="'positionSetting'+item.id"></videoComponent>
                    </div>
                    <div>
                        <Checkbox :label="item.id">{{item.name}}</Checkbox>
                    </div>
                </Col> 
            </CheckboxGroup>   
        </Row>
        <div slot="footer">
            <Button type="primary" @click="submitChoosedVideo()">确定</Button>    
        </div>    
    </Modal>    
</template>
<script>
import videoComponent from '@/components/Common/Video/VideoComponent'
export default {
    props: {
        videoInfo: {
            isShowVideo: {
                type: Boolean
            },
            videoData: {
                type: Array
            }
        }
    },
    components: { videoComponent },
    data(){
        return{
            curVideo: [],
            selectdVideo: []
        }
    },
    watch:{
        'videoInfo.isShowVideo': function(newVal){
        },
        'videoInfo.videoData': function(newVal){
            this.getVideoData(newVal)
        }
    },
    mounted(){

    },
    methods: {
        getVideoData(videoData){
            videoData.forEach(item => {
                var temp = {
                    id: item.id,
                    url: item.url,
                    tunnelId: item.tunnelId,
                    storeId: item.storeId,
                    areaId: item.areaId,
                    name: item.name
                }
            this.curVideo.push(temp)
            });
        },
        // changeCheckbox(selection) {
        // },
        submitChoosedVideo(){
            var childValue = {
                // isShowVideo: this.videoInfo.isShowVideo,
                selectdVideo: this.selectdVideo
            }
            this.$emit('childByValue',childValue)
        }
    }
}
</script>
<style scoped>
    .videoBox{
        height: 20vh;
        padding: 10px;
    }
</style>
