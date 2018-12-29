<template>
    <div>
        <Row class="expand-row">
            <Col span="8">
                <span class="expand-key">所属仓段：</span>
                <span class="expand-value">{{ row.defect.area.name }}</span>
            </Col>
            <Col span="8">
                <span class="expand-key">所属区域：</span>
                <span class="expand-value">{{ row.defect.store.name }}</span>
            </Col>
            <Col span="8">
                <span class="expand-key">缺陷名称：</span>
                <span class="expand-value">{{ row.defect.name }}</span>
            </Col>
            <Col span="8">
                <span class="expand-key">发现时间：</span>
                <span class="expand-value">{{ new Date(row.defect.createTime).format('yyyy-MM-dd hh:mm:s') }}</span>
            </Col>
            <Col span="8">
                <span class="expand-key">缺陷类型： </span>
                <span class="expand-value">{{ row.defect.typeName }}</span>
            </Col>
            <Col span="8" v-show="row.defect.type==2">
                <span class="expand-key">对象名： </span>
                <span class="expand-value">{{ row.defect.objName }}</span>
            </Col>
            <Col span="8">
                <span class="expand-key">危险等级： </span>
                <span class="expand-value">{{ row.defect.levelName }}</span>
            </Col>
            <Col span="24">
                <span class="expand-key">描述: </span>
                <span class="expand-value">{{ row.defect.description  }}</span>
            </Col>
        </Row>
    </div>
</template>
<script>
    export default {
        props: {
            row: Object
        }
    };
</script>
<style scoped>
td.ivu-table-expanded-cell {
    padding: 10px 15px;
    font-size: 12px;
}
</style>

