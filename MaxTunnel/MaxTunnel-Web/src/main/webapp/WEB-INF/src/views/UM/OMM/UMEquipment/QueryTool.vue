<template>
    <div class="allDiv">
        <Row class="queryCondition">
            <Col span="6">
                <span class="conditionTitle">仪表工具类型</span><span class="conditionTitle">：</span>
                <Select v-model="toolsConditions.typeId" style="width: 60%">
                    <Option value=null>所有</Option>
                    <Option v-for="item in toolsType" :value="item.id" :key="item.id">{{ item.name }}</Option>
                </Select>
            </Col>
            <Col span="6">
                <span class="conditionTitle">仪表工具型号</span><span class="conditionTitle">：</span>
                <Select v-model="toolsConditions.modelId" style="width: 60%">
                    <Option value=null>所有</Option>
                    <Option v-for="item in toolsModel" :key="item.id" :value="item.id">{{item.name}}</Option>
                </Select>
            </Col>
            <Col span="6">
                <span class="word64 conditionTitle">使用状态</span><span class="conditionTitle">：</span>
                <Select v-model="toolsConditions.usingStatusId" style="width: 60%">
                    <Option value=null>所有</Option>
                    <Option v-for="item in usingStatus" :key="item.val" :value="item.val">{{item.key}}</Option>
                </Select>
            </Col>
            <Col span="6">
                <span class="word63 conditionTitle">供应商</span><span class="conditionTitle">：</span>
                <Select v-model="toolsConditions.venderId" style="width: 60%">
                    <Option value=null>所有</Option>
                    <Option v-for="(item) in venders" :key="item.id" :value="item.id">{{item.name}}</Option>
                </Select>
            </Col>
            <Col span="6">
                <span class="word64 conditionTitle">库存状态</span><span class="conditionTitle">：</span>
                <Select v-model="toolsConditions.ventoryStatusId" style="width: 60%">
                    <Option value=null>所有</Option>
                    <Option v-for="item in inventoryStatus" :key="item.key" :value="item.key">{{item.val}}</Option>
                </Select>
            </Col>
            <Col span="6">
                <span class="conditionTitle">入库开始时间：</span>
                <DatePicker type="datetime" v-model="toolsConditions.InStorageStartTime" placeholder="请输入入库开始时间" style="width: 60%"></DatePicker>
            </Col>
            <Col span="6">
                <span class="conditionTitle">入库结束时间：</span>
                <DatePicker type="datetime" v-model="toolsConditions.InStorageEndTime" placeholder="请输入入库结束时间" style="width: 60%"></DatePicker>
            </Col>
            <Col span="6">
                <Button type="primary"  @click="showTable" icon="ios-search">查询</Button>
            </Col>
        </Row>
        <div class="list">
            <div class="toolBtn">
                <p>仪表工具</p>
                <Button type="success"  @click="batchReturn()">批量归还</Button>
                <Button type="error" @click="batchLend()">批量借出</Button>
                <Button type="primary" @click="add({path: '/UM/equipment/addTools'})">批量添加</Button>
            </div>
            <div class="nullData" v-show="isNullData">暂无数据</div>
            <Row :gutter="16">
                <Col span="6" v-for="item in toolData" :key="item.id" style="margin-top: 20px;">
                    <div class="toolBox boxBG">
                        <h2 class="toolTitle">{{item.name}}</h2>
                        <div class="toolInfo">
                            <p>仪表工具类型：{{item.typeName}}</p>
                            <p>仪表工具型号：{{item.modelName}}</p>
                            <p>使用状态：{{item.useStatusName}}</p>
                            <p>库存状态：<span :class="item.status ? 'trueStatus' : 'falseStatus'">
                                            <span v-if="item.status==true">在库</span>
                                            <span v-if="item.status==false">出库</span>
                                        </span>
                            </p>
                            <p>供应商：{{item.venderName}}</p>
                            <p>入库时间：{{item.inTime}}</p>
                        </div>
                        <div class="operations">
                            <div class="operation" @click="borrowSubmit(item.id)" v-show="item.status">
                                <span class="operationBtn borrow">借出</span>
                            </div>
                            <div class="operation" @click="returnSubmit(item.id)" v-show="!item.status">
                                <span class="operationBtn return">归还</span>
                            </div>
                            <div class="operation" @click="showHistory(item.id)">
                                <span class="operationBtn history">历史记录</span>
                            </div>
                            <!-- <div class="operation">删除</div> -->
                        </div>
                    </div>
                </Col>
            </Row>
        </div>
        <Page :total="page.pageTotal" :current="page.pageNum" :page-size="page.pageSize" show-total show-sizer
            placement="top" @on-change="handlePage" @on-page-size-change='handlePageSize' show-elevator
            :style='pageStyle'></Page>
        <!-- 批量借出 -->
        <Modal
            v-model="isBatchLend"
            title="批量借出"
            :width="modalWidth"
        >
            <Row class="queryConditions">
                <Col span="8">
                    <span>仪表名称</span><span>：</span>
                    <Input v-model="lendModalConditions.name" style="width: 60%" @on-blur="batchLend()"></Input>
                </Col>
                <Col span="8">
                    仪表工具类型：
                    <Select v-model="lendModalConditions.typeId" style="width: 60%" ref="typeId" @on-change="batchLend()">
                        <Option value=null key="0">所有</Option>
                        <Option v-for="item in toolsType" :value="item.id" :key="item.id">{{ item.name }}</Option>
                    </Select>
                </Col>
                <Col span="8">
                    开始时间：
                    <DatePicker ref="startTime" type="datetime" v-model="lendModalConditions.startTime" placeholder="请输入开始时间" style="width: 60%" @on-change="batchLend()"></DatePicker>
                </Col>
                <Col span="8">
                    <span style="letter-spacing: 0.5em;margin-right: -0.5em;">供应商</span><span>：</span>
                    <Select ref="venderId" v-model="lendModalConditions.venderId" style="width: 60%" @on-change="batchLend()">
                        <Option value=null key="0">所有</Option>
                        <Option v-for="(item) in venders" :key="item.id" :value="item.id">{{item.name}}</Option>
                    </Select>
                </Col>
                <Col span="8">
                    仪表工具型号：
                    <Select ref="modelId" v-model="lendModalConditions.modelId" style="width: 60%" @on-change="batchLend()">
                        <Option value=null key="0">所有</Option>
                        <Option v-for="item in toolsModel" :key="item.id" :value="item.id">{{item.name}}</Option>
                    </Select>
                </Col>
                <Col span="8">
                    结束时间：
                    <DatePicker ref="endTime" type="datetime" v-model="lendModalConditions.endTime" placeholder="请输入结束时间" style="width: 60%" @on-change="batchLend()"></DatePicker>
                </Col>
            </Row>

            <Table stripe border ref="selection" :columns="batchLendColums"  :data="batchLendData" @on-selection-change="checkTable" @on-selection-all="checkTable"></Table>

            <Page :total="batchLendPage.pageTotal" :current="batchLendPage.pageNum" :page-size="batchLendPage.pageSize" show-total show-sizer
                placement="top" @on-change="handlePageBatchLend" @on-page-size-change='handPageSizeBatchLend' show-elevator style="margin-top: 10px;text-align: right"></Page>

            <Row style="margin-top: 20px;">
                <Form ref="batchLendSubmitData" :model="batchLendSubmitData" :rules="ruleInline" inline>
                    <Col span="8">
                        <FormItem prop="staffId" style="width: 100%" class="borrower">
                            借用人：
                            <Select style="width: 60%;" v-model="batchLendSubmitData.staffId">
                                <Option v-for="item in staffs" :key="item.id" :value="item.id">{{item.name}}</Option>
                            </Select>
                        </FormItem>
                    </Col>
                    <Col span="8">
                        <FormItem prop="borrowTime" class="borrowTime">
                            借用时间：
                            <DatePicker type="datetime"  placeholder="请输入取用时间" style="width: 60%" v-model="batchLendSubmitData.borrowTime"></DatePicker>
                        </FormItem>
                    </Col>
                    <Col span="8">
                        <FormItem>
                            <span class="purpose">备注：</span>
                            <Input type="textarea" style="width: 70%;" v-model="batchLendSubmitData.describe"></Input>
                        </FormItem>
                    </Col>
                </Form>
            </Row>

            <div slot="footer">
                <Button type="text" size="large" @click="cancelBatchLend('batchLendSubmitData')">取消</Button>
                <Button type="primary" size="large" @click="confirmBatchLend('batchLendSubmitData')" :disabled="isBatchLendSubmit">确定</Button>
            </div>
        </Modal>
        <!-- 批量归还 -->
        <Modal
            v-model="isBatchReturn"
            title="批量归还"
           :width="modalWidth"
        >
            <Row class="queryConditions">
                <Col span="6">
                    <span>仪表名称</span><span>：</span>
                    <Input v-model="returnModalConditions.name" style="width: 60%"></Input>
                </Col>
                <Col span="6">
                    仪表工具类型：
                    <Select v-model="returnModalConditions.typeId" style="width: 60%" ref="typeId">
                        <Option value=null key="0">所有</Option>
                        <Option v-for="item in toolsType" :value="item.id" :key="item.id">{{ item.name }}</Option>
                    </Select>
                </Col>
                <Col span="6">
                    借用开始时间：
                    <DatePicker ref="startTime" type="datetime" v-model="returnModalConditions.startTime" placeholder="请输入开始时间" style="width: 60%"></DatePicker>
                </Col>
                <Col span="6">
                    <span style="letter-spacing: 0.5em;margin-right: -0.5em;">借用人</span><span>：</span>
                    <Select v-model="returnModalConditions.staffId" style="width: 60%">
                        <Option value=null key="0">所有</Option>
                        <Option v-for="item in staffs" :key="item.id" :value="item.id">{{item.name}}</Option>
                    </Select>
                </Col>
                <Col span="6">
                    <span style="letter-spacing: 0.5em;margin-right: -0.5em;">供应商</span><span>：</span>
                    <Select ref="venderId" v-model="returnModalConditions.venderId" style="width: 60%">
                        <Option value=null key="0">所有</Option>
                        <Option v-for="(item) in venders" :key="item.id" :value="item.id">{{item.name}}</Option>
                    </Select>
                </Col>
                <Col span="6">
                    仪表工具型号：
                    <Select ref="modelId" v-model="returnModalConditions.modelId" style="width: 60%">
                        <Option value=null key="0">所有</Option>
                        <Option v-for="item in toolsModel" :key="item.id" :value="item.id">{{item.name}}</Option>
                    </Select>
                </Col>
                <Col span="6">
                    借用结束时间：
                    <DatePicker ref="endTime" type="datetime" v-model="returnModalConditions.endTime" placeholder="请输入结束时间" style="width: 60%"></DatePicker>
                </Col>
                <Col span="6">
                    <Button type="primary" size="small" @click="batchReturn()" icon="ios-search">查询</Button>
                </Col>
            </Row>
           <Table stripe border   ref="returnSelection" :columns="batchReturnColums"  :data="batchReturnData" @on-selection-change="checkReturnTable" @on-selection-all="checkReturnTable"></Table>

            <Page :total="returnPage.pageTotal" :current="returnPage.pageNum" :page-size="returnPage.pageSize" show-total show-sizer
                placement="top" @on-change="handlePageBatchReturn" @on-page-size-change='handlePageSizeBatchReturn' show-elevator style="margin-top: 10px;text-align: right"></Page>

            <Row style="margin-top: 20px;">
                <Form ref="batchReturnSubmitData" :model="batchReturnSubmitData" :rules="returnRuleInline" inline>
                    <Col span="6">
                        <FormItem prop="staffId" style="width: 100%">
                            归还人：
                            <Select style="width: 60%;" v-model="batchReturnSubmitData.staffId">
                                <Option v-for="item in staffs" :key="item.id" :value="item.id">{{item.name}}</Option>
                            </Select>
                        </FormItem>
                    </Col>
                    <Col span="6">
                        <FormItem prop="returnTime">
                            归还时间：
                            <DatePicker type="datetime"  placeholder="请输入取用时间" style="width: 70%" v-model="batchReturnSubmitData.returnTime"></DatePicker>
                        </FormItem>
                    </Col>
                    <Col span="6">
                        <FormItem prop="usingStatus" style="width: 100%">
                            使用状态：
                            <Select style="width: 60%;" v-model="batchReturnSubmitData.usingStatus">
                                <Option v-for="item in returnUsingStatus" :key="item.val" :value="item.val">{{item.key}}</Option>
                            </Select>
                        </FormItem>
                    </Col>
                    <Col span="6">
                        <FormItem>
                            <span class="purpose">备注：</span>
                            <Input type="textarea" style="width: 70%;" v-model="batchReturnSubmitData.describe"></Input>
                        </FormItem>
                    </Col>
                </Form>
            </Row>

            <div slot="footer">
                <Button type="text" size="large" @click="cancelBatchReturn('batchReturnSubmitData')">取消</Button>
                <Button type="primary" size="large" @click="confirmBatchReturn('batchReturnSubmitData')" :disabled="isBatchReturnSubmit">确定</Button>
            </div>
        </Modal>
        <!-- 借出信息登记 -->
        <!-- <BorrowModal :isBorrow="isBorrow" :isBorrowId="isBorrowId" :onsubmit="showTable"></BorrowModal> -->
        <Modal
            v-model="isBorrow"
            title="借出信息登记"
            :width="minModalWidth"
        >
            <Form ref="borrow" :model="borrow" :rules="borrowRules" :label-width="140">
                <FormItem label="借用人：" prop="staffId">
                    <Select v-model="borrow.staffId" style="width: 70%">
                        <Option v-for="item in staffs" :key="item.id" :value="item.id">{{item.name}}</Option>
                    </Select>
                </FormItem>
                <FormItem label="借用时间：" prop="borrowTime">
                    <DatePicker type="datetime"  placeholder="请输入取用时间" style="width: 70%" v-model="borrow.borrowTime"></DatePicker>
                </FormItem>
                <FormItem label="借用备注：">
                    <Input type="textarea" v-model="borrow.Describe" style="width: 70%"></Input>
                </FormItem>
            </Form>
            <div slot="footer">
                <Button type="default" @click="cancelBorrow('borrow')">取消</Button>
                <Button type="primary" @click="confirmBorrow('borrow')" :disabled="confirmBorrowBtn">确定</Button>
            </div>
        </Modal>
        <!-- 归还信息登记 -->
        <Modal
            v-model="isReturn"
            title="归还信息登记"
            :width="minModalWidth"
        >
            <Form ref="toolReturn" :model="toolReturn" :rules="returnRules" :label-width="140">
                <FormItem label="归还人：" prop="staffId">
                    <Select v-model="toolReturn.staffId" style="width: 70%">
                        <Option v-for="item in staffs" :key="item.id" :value="item.id">{{item.name}}</Option>
                    </Select>
                </FormItem>
                <FormItem label="归还时间：" prop="returnTime">
                    <DatePicker type="datetime"  placeholder="请输入取用时间" style="width: 70%" v-model="toolReturn.returnTime"></DatePicker>
                </FormItem>
                <FormItem label="使用状态：" prop="usingStatus">
                    <Select v-model="toolReturn.usingStatus" style="width: 70%">
                        <Option v-for="item in returnUsingStatus" :key="item.val" :value="item.val">{{item.key}}</Option>
                    </Select>
                </FormItem>
                <FormItem label="借用备注：">
                    <Input type="textarea" v-model="toolReturn.Describe" style="width: 70%"></Input>
                </FormItem>
            </Form>

            <div slot="footer">
                <Button type="default" @click="cancelReturn('toolReturn')">取消</Button>
                <Button type="primary" @click="confirmReturn('toolReturn')" :disabled="confirmReturnBtn">确定</Button>
            </div>
        </Modal>
        <!-- 历史记录查询 -->
        <Modal
            v-model="isSHowHistory"
            title="历史记录"
            :width="modalWidth"
        >
            <Row class="queryCondition">
                <Col span="8">
                    借用人：
                    <Select style="width: 60%;" v-model="historyConditions.borrowerId">
                        <Option key="0" value=null>所有</Option>
                        <Option v-for="item in staffs" :key="item.id" :value="item.id">{{item.name}}</Option>
                    </Select>
                </Col>
                <Col span="8">
                    借用开始时间：
                    <DatePicker type="datetime"  placeholder="请输入借用开始时间" style="width: 60%" v-model="historyConditions.borrowStartTime"></DatePicker>
                </Col>
                <Col span="8">
                    借用结束时间：
                    <DatePicker type="datetime"  placeholder="借用结束时间" style="width: 60%" v-model="historyConditions.borrowEndTime"></DatePicker>

                </Col>
                <Col span="8">
                    归还人：
                    <Select v-model="historyConditions.returnId" style="width: 60%">
                        <Option key="0" value=null>所有</Option>
                        <Option v-for="item in staffs" :key="item.id" :value="item.id">{{item.name}}</Option>
                    </Select>
                </Col>
                <Col span="8">
                    归还开始时间：
                    <DatePicker type="datetime"  placeholder="请输入归还开始时间" style="width: 60%" v-model="historyConditions.returnStartTime"></DatePicker>
                </Col>
                <Col span="8">
                    归还结束时间：
                    <DatePicker type="datetime"  placeholder="请输入借用归还时间" style="width: 60%" v-model="historyConditions.returnEndTime"></DatePicker>

                </Col>
            </Row>
            <Table stripe border height="330"  :columns="historyColums"  :data="historyData"></Table>

            <Page :total="historyPage.pageTotal" :current="historyPage.pageNum" :page-size="historyPage.pageSize" show-total show-sizer
                placement="top" @on-change="handlePageHistory" @on-page-size-change='handPageSizeHistory' show-elevator style="margin-top: 10px;text-align: right"></Page>
        </Modal>
    </div>
</template>
<script>
import { EquipmentService } from "../../../../services/equipmentService";
import { TunnelService } from '../../../../services/tunnelService';
import BorrowModal from './borrowModalQT.vue'
export default {
    components: {BorrowModal},
    data(){
        return{
            isShow: true,
            toolsType:[],
            toolsModel:[],
            venders:[],
            staffs: [],
            //仪表工具使用状态
            usingStatus: [
                // { key: 0, val: '损坏' },
                // { key: 1, val: '正常' }
            ],
            //库存状态
            inventoryStatus: [
                { key: 0, val: '出库' },
                { key: 1, val: '在库' }
            ],
            toolsConditions: {
                typeId: null,
                InStorageStartTime: null,
                InStorageEndTime: null,
                modelId: null,
                usingStatusId: null,
                venderId: null,
                ventoryStatusId: null
            },
            toolData: [],
            pageStyle: {
                position: "absolute",
                bottom: "20px",
                right: "15px"
            },
            page: {
                pageTotal: 0,
                pageNum: 1,
                pageSize: 12
            },
            //批量借出
            isBatchLend: false,
            isBatchLendSubmit: false,
            lendModalConditions: {
                name: null,
                typeId: null,
                modelId: null,
                venderId: null,
                startTime: null,
                endTime: null
            },
            batchLendPage: {
                pageTotal: 0,
                pageNum: 1,
                pageSize: 6
            },
            batchLendColums: [
                {
                    type: 'selection',
                    width: 60,
                    align: 'center'
                },{
                    type: 'index',
                    width: 60,
                    align: 'center'
                },{
                    title: '备品名称',
                    key: 'name',
                    align: 'center'
                },{
                    title: '备品类别',
                    key: 'typeName',
                    align: 'center'
                },{
                    title: '备品型号',
                    key: 'modelName',
                    align: 'center'
                },{
                    title: '供应商',
                    key: 'venderName',
                    align: 'center'
                },{
                    title: '入库时间',
                    key: 'inTime',
                    align: 'center'
                }
            ],
            batchLendData: [],
            batchLendSubmitData: {
                staffId: null,
                borrowTime: null,
                describe: null,
                ids: null
            },
            ruleInline: {
                staffId:[
                    { type: 'number', required: true, message: '请选择借用人', trigger: 'change' }
                ],
                borrowTime: [
                    { type: 'date', required: true, message: '请选择借用时间', trigger: 'change'}
                ]
            },
            //批量归还
            isBatchReturn: false,
            isBatchReturnSubmit: false,
            batchReturnColums:[
                {
                    type: 'selection',
                    width: 60,
                    align: 'center'
                },{
                    type: 'index',
                    width: 60,
                    align: 'center'
                },{
                    title: '备品名称',
                    key: 'name',
                    align: 'center'
                },{
                    title: '备品类别',
                    key: 'typeName',
                    align: 'center'
                },{
                    title: '备品型号',
                    key: 'modelName',
                    align: 'center'
                },{
                    title: '供应商',
                    key: 'venderName',
                    align: 'center'
                },{
                    title: '入库时间',
                    key: 'inTime',
                    align: 'center',
                },{
                    title: '借用人',
                    key: 'staffName',
                    align: 'center',
                },{
                    title: '借用时间',
                    key: 'borrowTime',
                    align: 'center',
                },{
                    title: '借用备注',
                    key: 'describe',
                    align: 'center',
                    width: 120,
                    render: (h,param) => {
                        let temp = ''
                        if(param.row.describe!=null){
                            if(param.row.describe.length>10){
                                temp = param.row.describe.substr(0,10)+'...'
                            }else{
                                temp = param.row.describe
                            }
                        }
                        return h(
                            'div',
                            {
                                domProps: {
                                    title: param.row.describe
                                }
                            },
                            temp
                        )
                    }
                }
            ],
            batchReturnData: [],
            returnModalConditions: {
                name: null,
                typeId: null,
                modelId: null,
                venderId: null,
                staffId: null,
                startTime: null,
                endTime: null,
            },
            returnPage: {
                pageNum: 1,
                pageSize: 6,
                pageTotal: 0
            },
            returnUsingStatus: [
                // { key: '正常', val: 1 },
                // { key: '损坏', val: 0 }
            ],
            batchReturnSubmitData:{
                ids: null,
                staffId: null,
                returnTime: null,
                usingStatus: null,
                describe: null
            },
            returnRuleInline: {
                staffId: [
                    { type: 'number', required: true, message: '请选择归还人', trigger: 'change' }
                ],
                returnTime: [
                    { type: 'date', required: true, message: '请选择归还时间', trigger: 'change'}
                ],
                usingStatus: [
                    { type: 'number', required: true, message: '请选择设备使用状态', trigger: 'change' }
                ]
            },
            //单独借出
            isBorrow: false,
            confirmBorrowBtn: false,
            isAloneBorrow: false,
            borrow:{
                staffId: null,
                borrowTime: null,
                Describe: null
            },
            borrowRules: {
                staffId: [
                    { type: 'number', required: true, message: '请选择借用人', trigger: 'change' }
                ],
                borrowTime: [
                    { type: 'date', required: true, message: '请选择借出时间', trigger: 'change' }
                ]
            },
            isBorrowId: null,
            //单出归还
            isReturn: false,
            confirmReturnBtn: false,
            isAloneReturn: false,
            toolReturn: {
                staffId: null,
                returnTime: null,
                usingStatus: null,
                Describe: null
            },
            returnRules: {
                staffId: [
                    { type: 'number', required: true, message: '请选择归还人', trigger: 'change' }
                ],
                returnTime: [
                    { type: 'date', required: true, message: '请选择归还时间', trigger: 'change' }
                ],
                usingStatus: [
                    { type: 'number', required: true, message: '请选择仪表使用状态', trigger: 'change' }
                ]
            },
            isReturnId: null,
            //历史记录
            isSHowHistory: false,
            historyConditions: {
                borrowerId: null,
                borrowStartTime: null,
                borrowEndTime: null,
                returnId: null,
                returnStartTime: null,
                returnEndTime: null
            },
            historyColums: [
                {
                    type: 'index',
                    width: 60,
                    align: 'center'
                },
                {
                    title: '借用人',
                    key: 'staffName',
                    align: 'center'
                },
                {
                    title: '借出时间',
                    key: 'borrowTime',
                    align: 'center'
                },
                {
                    title: '借出时备注',
                    key: 'describe',
                    align: 'center'
                },
                {
                    title: '归还人',
                    key: 'returnName',
                    align: 'center'
                },
                {
                    title: '归还时间',
                    key: 'returnTime',
                    align: 'center'
                },
                {
                    title: '归还时备注',
                    key: 'remark',
                    align: 'center'
                }
            ],
            historyData: [],
            historyPage: {
                pageNum: 1,
                pageSize: 6,
                pageTotal: 0
            },
            modalWidth: null,
            minModalWidth: null,
            isNullData: false
        }
    },
    computed: {
        params(){
            let param = {
                name: this.toolsConditions.name,
                typeId: this.toolsConditions.typeId,
                modelId: this.toolsConditions.modelId,
                venderId: this.toolsConditions.venderId,
                useStatus: this.toolsConditions.usingStatusId,
                status: this.toolsConditions.ventoryStatusId,
                startTime: this.toolsConditions.InStorageStartTime,
                endTime: this.toolsConditions.InStorageEndTime,
                pageSize: this.page.pageSize,
                pageNum: this.page.pageNum
            }
            return Object.assign({}, param);
        },
        batchLendParams(){
            let param = {
                name: this.lendModalConditions.name,
                typeId: this.lendModalConditions.typeId,
                modelId: this.lendModalConditions.modelId,
                startTime: this.lendModalConditions.startTime,
                endTime: this.lendModalConditions.endTime,
                venderId: this.lendModalConditions.venderId,
                useStatus: 1,
                status: true,
                pageNum: this.batchLendPage.pageNum,
                pageSize: this.batchLendPage.pageSize
            }
            return Object.assign({}, param)
        },
        batchReturnParams(){
            let param = {
                name: this.returnModalConditions.name,
                modelId: this.returnModalConditions.modelId,
                typeId: this.returnModalConditions.typeId,
                venderId: this.returnModalConditions.venderId,
                useStatus: 1,
                status: false,
                staffId: this.returnModalConditions.staffId,
                startTime: this.returnModalConditions.startTime,
                endTime: this.returnModalConditions.endTime,
                pageSize: this.returnPage.pageSize,
                pageNum: this.returnPage.pageNum
            }
            return Object.assign({},param)
        }
    },
    mounted(){
        //获取type
        EquipmentService.getEquipmentTypes().then(
            res=>{
                this.toolsType = res
            },
            error => {
                this.Log.info(error);
            },
        );
        //获取model
        EquipmentService.getEquipmentModels().then(
            res=>{
                this.toolsModel = res
            },
            error => {
                this.Log.info(error)
            }
        )
        //获取供应商
        EquipmentService.getVenders().then(
            res=>{
                this.venders = res
            },
            error=>{
                this.Log.info(error)
            }
        )
        //获取借用人列表
        this.axios.get('staffs').then(res=>{
            let{ code,data } = res.data
            if(code==200){
                this.staffs = data
            }
        })
        //获取tool状态
        EquipmentService.getStatus().then(
            res=>{
                this.usingStatus = res
                this.returnUsingStatus = res
            },
            error=>{
                this.Log.info(error)
            }
        )
        this.showTable()
        this.getModalWidth()
    },
    methods: {
        add(path){
            this.$router.push(path)
        },
        //首页查询
        showTable(){
          if(new Date(this.toolsConditions.InStorageStartTime)>new Date(this.toolsConditions.InStorageEndTime)){
            this.$Message.error('开始时间必须小于结束时间！');
            return;
          }

            EquipmentService.queryTools(this.params).then(
                result => {
                    if(result.pagedList.length==0){
                        this.isNullData = true
                    }else{
                        this.isNullData = false
                    }
                    for(let index in result.pagedList){
                        result.pagedList[index].inTime = new Date(result.pagedList[index].inTime).format("yyyy-MM-dd hh:mm:ss");
                        if(result.pagedList[index].status == false){
                            this.isAloneReturn = true
                            this.isAloneBorrow = false
                        }else{
                            this.isAloneBorrow = true
                            this.isAloneReturn = false
                        }
                    }
                    this.toolData = result.pagedList
                    this.page.pageTotal = result.total
                },
                error => {
                    this.Log.info(error)
                }
            )
        },
        //首页分页
        handlePage(value) {
            this.page.pageNum = value;
            this.showTable();
        },
        handlePageSize(value) {
            this.page.pageSize = value;
            this.showTable();
        },
        //批量借出
        batchLend(){
            this.isBatchLend = true
            EquipmentService.queryTools(this.batchLendParams).then(
                result => {
                    for(let index in result.pagedList){
                        result.pagedList[index].inTime = new Date(result.pagedList[index].inTime).format("yyyy-MM-dd hh:mm:ss");
                    }
                    this.batchLendData = result.pagedList
                    this.batchLendPage.pageTotal = result.total
                },
                error => {
                    this.Log.info(error)
                }
            )
        },
        handlePageBatchLend(value){
            this.batchLendPage.pageNum = value
            this.batchLend()
        },
        handPageSizeBatchLend(value){
            this.batchLendPage.pageSize = value
            this.batchLend()
        },
        //获取ids
        checkTable(selection){
            this.selection = selection;
            var str = ''
            this.selection.forEach(element=>{
                str += element.id+","
            })
            this.batchLendSubmitData.ids = str.substr(0,str.length-1)
            console.log(this.batchLendSubmitData.ids)
        },
        //提交
        confirmBatchLend(name){
            this.isBatchLendSubmit = true
            setTimeout(()=>{
                this.isBatchLendSubmit = false
                this.$refs[name].validate((valid)=>{
                    if(valid){
                        EquipmentService.batchLend(this.batchLendSubmitData.ids,this.batchLendSubmitData).then(
                            result => {
                                this.isBatchLend = false
                                this.showTable()
                                this.$refs[name].resetFields()
                                this.batchLendSubmitData.describe = null
                            },
                            error => {
                                this.Log.info(error)
                            }
                        )
                    }else{
                        this.$Message.error("请填写正确的批量借出信息")
                    }
                })
            },2000)
        },
        //取消
        cancelBatchLend(name){
            this.$refs[name].resetFields()
            this.isBatchLend = false
        },
        //批量归还
        batchReturn(){
            this.isBatchReturn = true;
            EquipmentService.queryHisRecord(this.batchReturnParams).then(
                result => {
                    for(let index in result.pagedList){
                        result.pagedList[index].inTime = new Date(result.pagedList[index].inTime).format("yyyy-MM-dd hh:mm:ss");
                        result.pagedList[index].borrowTime = new Date(result.pagedList[index].borrowTime).format("yyyy-MM-dd hh:mm:ss");
                    }
                    this.batchReturnData = result.pagedList
                    this.returnPage.pageTotal = result.total
                },
                error => {
                    this.Log.info(error)
                }
            )
        },
        handlePageBatchReturn(value){
            this.returnPage.pageNum = value
            this.batchReturn()
        },
        handlePageSizeBatchReturn(value){
            this.returnPage.pageSize = value
            this.batchReturn()
        },
        //获取ids
        checkReturnTable(selection){
            this.selection = selection;
            var str = ''
            this.selection.forEach(element=>{
                str += element.id+","
            })
            this.batchReturnSubmitData.ids = str.substr(0,str.length-1)
            console.log(this.batchReturnSubmitData.ids)
        },
        //确认
        confirmBatchReturn(name){
            this.isBatchReturnSubmit = true
            setTimeout(()=>{
                this.isBatchReturnSubmit = false
                this.$refs[name].validate((valid)=>{
                    if(valid){
                        let batchReturnParams={
                            ids: this.batchReturnSubmitData.ids,
                            returnId: this.batchReturnSubmitData.staffId,
                            returnTime: this.batchReturnSubmitData.returnTime,
                            useStatus: this.batchReturnSubmitData.usingStatus,
                            remark: this.batchReturnSubmitData.describe
                        }
                        EquipmentService.batchReturn(this.batchReturnSubmitData.ids,this.batchReturnSubmitData.usingStatus,batchReturnParams,).then(
                            result => {
                                this.isBatchReturn = false
                                this.showTable()
                                this.$refs[name].resetFields()
                                this.batchReturnSubmitData.describe = null
                                this.$Message.success("归还成功!")
                            },
                            error => {
                                this.Log.info(error)
                            }
                        )
                    }else{
                       this.$Message.error("请填写正确的批量归还信息")
                    }
                })
            },2000)
        },
        cancelBatchReturn(name){
            this.$refs[name].resetFields()
            this.isBatchReturn = false
        },
        //单独借出
        borrowSubmit(id){
            this.isBorrow = true
            this.isBorrowId = id
        },
        confirmBorrow(name){
            this.confirmBorrowBtn = true
            setTimeout(()=>{
                this.confirmBorrowBtn = false
                this.$refs[name].validate((valid) => {
                    if(valid){
                        let lendParams={
                            ids: this.isBorrowId,
                            staffId: this.borrow.staffId,
                            borrowTime: this.borrow.borrowTime,
                            Describe: this.borrow.describe
                        }
                        EquipmentService.batchLend(this.isBorrowId,lendParams).then(
                            result => {
                                this.isBorrow = false
                                this.showTable()
                            },
                            error => {
                                this.Log.info(error)
                            }
                        )
                        this.$refs[name].resetFields()
                        this.borrow.describe = null
                    }else{
                        this.$Message.error("请填写正确的借出信息")
                    }
                })
            },2000)
        },
        cancelBorrow(name){
            this.$refs[name].resetFields()
            this.isBorrow = false
        },
        //单出归还
        returnSubmit(id){
            this.isReturn = true
            this.isReturnId = id
        },
        confirmReturn(name){
            this.confirmReturnBtn = true
            setTimeout(()=>{
                this.confirmReturnBtn = false
                this.$refs[name].validate((valid) => {
                    if(valid){
                        let returnParams={
                            ids: this.isReturnId,
                            returnId: this.toolReturn.staffId,
                            returnTime: this.toolReturn.returnTime,
                            useStatus: this.toolReturn.usingStatus,
                            remark: this.toolReturn.describe
                        }
                        EquipmentService.batchReturn(this.isReturnId,this.toolReturn.usingStatus,returnParams).then(
                            result => {
                                this.isReturn = false
                                this.showTable()
                            },
                            error => {
                                this.Log.info(error)
                            }
                        )
                    }else{
                        this.$Message.error("请填写正确的归还信息")
                    }
                })
            },2000)
        },
        cancelReturn(name){
            this.$refs[name].resetFields()
            this.isReturn = false
        },
        //历史查询
        handlePageHistory(value){
            this.historyPage.pageNum = value
        },
        handPageSizeHistory(value){
            this.historyPage.pageSize = value
        },
        showHistory(id){
            this.isSHowHistory = true
            let hisParams = {
                instrumentId: id,
                staffId: this.historyConditions.borrowerId,
                startTime: this.historyConditions.borrowStartTime,
                endTime: this.historyConditions.endTime,
                returnId: this.historyConditions.returnId,
                retStaTime: this.historyConditions.returnStartTime,
                retEndTime: this.historyConditions.returnEndTime,
                pageNum: this.historyPage.pageNum,
                pageSize: this.historyPage.pageSize
            }
            EquipmentService.queryHisRecord(hisParams).then(
                result => {
                    this.historyData = result.pagedList
                    this.historyPage.pageTotal = result.total
                },
                error => {
                    this.Log.info(error)
                }
            )
        },
        //modal赋值宽度
        getModalWidth(){
            this.modalWidth = document.body.offsetWidth*0.7
            this.minModalWidth = document.body.offsetWidth*0.3
        }
    }
}
</script>
<style scoped>
.word64{
    letter-spacing: 0.667em;
    margin-right: -0.667em;
}
.word63{
    letter-spacing: 1.5em;
    margin-right: -1.5em;
}
.queryConditions{
    line-height: 40px;
    background: #ffffff;
    padding-bottom: 5px;
    padding-left: 5px;
}
.toolBox{
    color: #fff;
}
.toolInfo{
    width: 75%;
    margin: 0 auto;
}
.toolInfo p{
    line-height: 35px;
}
.toolBtn{
    line-height: 45px;
}
.toolBtn p{
    display: inline-block;
    font-size: 22px;
    font-weight: 600;
    color: #fff;
}
.toolBtn button{
    float: right;
    margin-right: 5px;
}
.operation{
    display: inline-block;
    width: 49%;
    line-height: 50px;
    text-align: center;
    cursor: pointer;
    font-size: 14px;
}
.operation:hover{
    color: #357aa1;
    /* font-size: 15px; */
}
.purpose{
    display: inline-block;
    vertical-align: top;
}
.queryConditions{
    line-height: 35px;
}
/*在库与否*/
.trueStatus{
    color: green
}
.falseStatus{
    color: red;
}
.ivu-page>>>.ivu-page-total, .ivu-page>>>.ivu-page-options-elevator{
    color: #fff;
}
.ivu-btn-primary{
    background: linear-gradient(to left, #2734e1, #b195ed);
    border-color: #2734e1;
}
.ivu-btn-error{
    background: linear-gradient(to left, #f61a1a, #f68380);
    border-color: #f61a1a;
}
.ivu-btn-success{
    background: linear-gradient(to left, #1af6b0, #a7ecd7);
    border-color: #1af6b0;
}
.boxBG{
    background: url("../../../../assets/UM/boxBGImg.png") no-repeat;
    background-size: 100% 100%;
}
.toolTitle{
    text-align: center;
    padding-top: 2vmin;
}
.operationBtn{
    padding: 0.7vmin 1vmin;
    border-radius: 1.2vmin;
}
.operationBtn.return,.operationBtn.borrow{
    background: linear-gradient(to left, #2734e1, #7c83f2)
}
.operationBtn.history{
    background: linear-gradient(to left, #1fed2d, #8de391)
}
@media (min-width: 2200px){
    .ivu-select,.ivu-select >>> .ivu-select-selection,.ivu-input-wrapper >>> .ivu-input,.ivu-date-picker >>> .ivu-input,
    .ivu-select.ivu-select-single >>> .ivu-select-selected-value,.ivu-select.ivu-select-single >>> .ivu-select-placeholder
    {
        height: 4vmin;
        line-height: 4vmin;
        font-size: 1.4vmin;
    }
    .queryCondition{
        font-size: 1.4vmin;
    }
    h2{
        font-size: 2.5vmin;
    }
    .toolInfo p,.operation{
        line-height: 4.5vmin;
        font-size: 1.6vmin;
    }
    /* .operation:hover{
        font-size: 1.7vmin;
    } */
    .toolBtn p{
        font-size: 2.5vmin;
    }
    .toolTitle,.toolBtn{
        line-height: 4vmin;
    }
    .ivu-form-item >>> .ivu-form-item-label{
        width: 15vmin !important;
        line-height: 4.5vmin;
    }
    .ivu-form-item >>> .ivu-form-item-content{
        margin-left: 15vmin !important;
        line-height: 4.5vmin;
    }
    /* .operation:hover{
        font-size: 1.6vmin;
    } */
}
</style>
