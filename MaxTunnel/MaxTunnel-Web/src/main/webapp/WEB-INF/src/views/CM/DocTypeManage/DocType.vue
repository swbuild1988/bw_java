<template>
    <div>
        <h1 style="text-align:center;margin-bottom:20px;margin-top:10px">文件类型管理</h1>
        <Row style="marginLeft:25px;marginBottom:10px;">
            <Col span="6">
                <span>类型名称</span>
                <span>：</span>
                <Input v-model="searchInfo.name" placeholder="支持模糊查询" class="inputWidth"/>
            </Col>
            <Col span="6">
                <span>开始时间</span>
                <span>：</span>
                <DatePicker
                    type="datetime"
                    placeholder="请选择开始时间"
                    class="inputWidth"
                    v-model="searchInfo.startTime"
                ></DatePicker>
            </Col>
            <Col span="6">
                <span>结束时间</span>
                <span>：</span>
                <DatePicker
                    type="datetime"
                    placeholder="请选择结束时间"
                    class="inputWidth"
                    v-model="searchInfo.endTime"
                ></DatePicker>
            </Col>
            <Col span="6">
                <Button
                    type="primary"
                    size="small"
                    icon="ios-search"
                    @click="search"
                    style="text-align:right;"
                >查询</Button>
            </Col>
        </Row>
        <div class="treeWrapper">
            <Tree :data="doctypes" :render="renderContent"></Tree>
        </div>
    </div>
</template>
<script>
import { FileService } from "../../../services/fileService";
export default {
    name: "doctype_manage",
    data() {
        return {
            searchInfo: {
                name: null,
                startTime: null,
                endTime: null
            },
            doctypes: [
                {
                    name: "文件类型",
                    expand: true,
                    render: (h, { root, node, data }) => {
                        return h(
                            "span",
                            {
                                style: {
                                    display: "inline-block",
                                    width: "100%"
                                }
                            },
                            [
                                h("span", [
                                    h("Icon", {
                                        props: {
                                            type: "ios-folder-outline"
                                        },
                                        style: {
                                            marginRight: "8px"
                                        }
                                    }),
                                    h("span", data.name)
                                ]),
                                h(
                                    "span",
                                    {
                                        style: {
                                            display: "inline-block",
                                            float: "right",
                                            marginRight: "32px"
                                        }
                                    },
                                    [
                                        h("Button", {
                                            props: Object.assign(
                                                {},
                                                this.buttonProps,
                                                {
                                                    icon: "ios-plus-empty",
                                                    type: "primary",
                                                    disabled: this.btnDisabled
                                                }
                                            ),
                                            style: {
                                                width: "52px"
                                            },
                                            on: {
                                                click: () => {
                                                    this.append(data);
                                                }
                                            }
                                        })
                                    ]
                                )
                            ]
                        );
                    },
                    children: []
                }
            ],
            buttonProps: {
                type: "ghost",
                size: "small"
            },
            addInfo: [],
            btnDisabled: false
        };
    },
    mounted() {
        this.search();
    },
    methods: {
        renderContent(h, { root, node, data }) {
            let content = data.saveFlag
                ? [
                      h("span", [
                          h("Icon", {
                              props: {
                                  type: "ios-paper-outline"
                              },
                              style: {
                                  marginRight: "8px"
                              }
                          }),
                          h("span", data.name)
                      ]),
                      h(
                          "span",
                          {
                              style: {
                                  display: "inline-block",
                                  float: "right",
                                  marginRight: "32px"
                              }
                          },
                          [
                              h("Button", {
                                  props: Object.assign({}, this.buttonProps, {
                                      icon: "ios-plus-empty",
                                      disabled: this.btnDisabled
                                  }),
                                  style: {
                                      marginRight: "8px"
                                  },
                                  on: {
                                      click: () => {
                                          this.append(data);
                                      }
                                  }
                              }),
                              h("Button", {
                                  props: Object.assign({}, this.buttonProps, {
                                      icon: "ios-minus-empty",
                                      disabled: this.btnDisabled
                                  }),
                                  on: {
                                      click: () => {
                                          this.del(data);
                                      }
                                  }
                              })
                          ]
                      )
                  ]
                : [
                      h("span", [
                          h("Icon", {
                              props: {
                                  type: "ios-paper-outline"
                              },
                              style: {
                                  marginRight: "8px"
                              }
                          }),
                          h("Input", {
                              props: {
                                  placeholder: "类型名称"
                              },
                              style: {
                                  width: "30vmin"
                              },
                              on: {
                                  "on-change": event => {
                                      this.addInfo[node.nodeKey] = {
                                          name: event.target.value,
                                          parentId: data.parentId
                                      };
                                  }
                              }
                          })
                      ]),
                      h(
                          "span",
                          {
                              style: {
                                  display: "inline-block",
                                  float: "right",
                                  marginRight: "32px"
                              }
                          },
                          [
                              h("Button", {
                                  props: {
                                      icon: "checkmark-round",
                                      type: "primary",
                                      size: "small"
                                  },
                                  style: {
                                      marginRight: "8px"
                                  },
                                  on: {
                                      click: () => {
                                          this.save(node);
                                      }
                                  }
                              }),
                              h("Button", {
                                  props: {
                                      icon: "close-round",
                                      type: "primary",
                                      size: "small"
                                  },
                                  on: {
                                      click: () => {
                                          this.remove(root, node, data);
                                      }
                                  }
                              })
                          ]
                      )
                  ];
            return h(
                "span",
                {
                    style: {
                        display: "inline-block",
                        width: "100%"
                    }
                },
                content
            );
        },
        append(data) {
            this.btnDisabled = true;
            const children = data.children || [];
            children.push({
                name: "",
                expand: true,
                saveFlag: false,
                parentId: data.id
            });
            this.$set(data, "children", children);
        },
        del(data) {
            this.$Modal.confirm({
                title: "文档类型",
                content: "<p>确定删除" + data.name + "吗？</p>",
                onOk: () => {
                    FileService.deleteDocType(data.id).then(
                        result => {
                            this.search();
                        },
                        error => {
                            this.Log.info(error);
                        }
                    );
                }
            });
        },
        search() {
            this.searchInfo.name && this.searchInfo.name.length
                ? this.searchInfo.name
                : null;
            FileService.getDocTypeTree(this.searchInfo).then(
                res => {
                    this.doctypes[0].children = res;
                    this.addNodeAttr(this.doctypes[0].children);
                },
                err => this.Log.info(err)
            );
        },
        addNodeAttr(arr) {
            arr.map(doc => {
                doc.expand = true;
                doc.saveFlag = true;
                if (doc.children) {
                    this.addNodeAttr(doc.children);
                }
            });
        },
        remove(root, node, data) {
            const parentKey = root.find(el => el === node).parent;
            const parent = root.find(el => el.nodeKey === parentKey).node;
            const index = parent.children.indexOf(data);
            parent.children.splice(index, 1);
            this.btnDisabled = false;
        },
        save(node) {
            let curNode = this.addInfo.find(
                (item, index) => index === node.nodeKey
            );

            FileService.addDocType(curNode).then(
                res => {
                    this.search();
                    this.btnDisabled = false;
                },
                err => this.Log.info(err)
            );
        }
    }
};
</script>
<style scoped>
.inputWidth {
    width: 78%;
}
.treeWrapper {
    width: 80%;
    margin: 4vmin auto;
}
</style>

