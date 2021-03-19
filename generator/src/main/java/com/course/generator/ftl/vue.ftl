<template>
    <div>
        <p>
            <!-- 新增按钮 -->
            <button class="btn btn-white btn-default btn-round" @click="toadd()">
                <i class="ace-icon fa fa-edit"></i>新 增
            </button>
            &nbsp;
            <!-- 刷新按钮 -->
            <button class="btn btn-white btn-default btn-round" @click="list(1)">
                <i class="ace-icon fa fa-refresh"></i>刷 新
            </button>
        </p>
        <!-- 分页 -->
        <pagination ref="pagination" v-bind:list="list"></pagination>
        <!-- 表格 -->
        <table id="simple-table" class="table  table-bordered table-hover">
            <thead>
            <tr>
                <#list fieldList as field>
                    <th>${field.nameCn}</th>
                </#list>
                <th class="hidden-480">操作</th>
            </tr>
            </thead>

            <tbody>
            <tr v-for="${domain} in ${domain}s" v-bind:key="${domain}.id">
                <#list fieldList as field>
                    <td>{{${domain}.${field.nameHump}}}</td>
                </#list>
                <td>
                    <div class="hidden-sm hidden-xs btn-group">
                        <!-- 编辑 -->
                        <button class="btn btn-xs btn-info" @click="toupdate(${domain})">
                            <i class="ace-icon fa fa-pencil bigger-120"></i>
                        </button>
                        <!-- 删除 -->
                        <button class="btn btn-xs btn-danger" @click="del(${domain}.id)">
                            <i class="ace-icon fa fa-trash-o bigger-120"></i>
                        </button>
                    </div>
                </td>
            </tr>
            </tbody>
        </table>
        <!-- 新增模态框 -->
        <div id="${domain}-add-model" class="modal fade" tabindex="-1" role="dialog">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button
                                type="button"
                                class="close"
                                data-dismiss="modal"
                                aria-label="Close"
                        >
                            <span aria-hidden="true">&times;</span>
                        </button>
                        <h4 class="modal-title">表单</h4>
                    </div>
                    <div class="modal-body">
                        <!-- 新增表单 -->
                        <form class="form-horizontal">

                                <#list fieldList as field>
                                    <div class="form-group">
                                     <#if field.nameHump!="createdAt" && field.nameHump!="updatedAt" && field.nameHump!="sort">
                                        <label class="col-sm-2 control-label">${field.nameCn}</label>
                                        <div class="col-sm-10">
                                        <input
                                                type="text"
                                                class="form-control"
                                                placeholder="${field.nameCn}"
                                                v-model="${domain}.${field.nameHump}"
                                        />
                                        </div>
                                     </#if>
                                     </div>
                                </#list>

                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">
                            取消
                        </button>
                        <button type="button" class="btn btn-primary" v-on:click="save()">
                            保存
                        </button>
                    </div>
                </div>
            </div>
        </div>
        <!-- 编辑模态框 -->
        <div
                id="${domain}-update-model"
                class="modal fade"
                tabindex="-1"
                role="dialog"
        >
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button
                                type="button"
                                class="close"
                                data-dismiss="modal"
                                aria-label="Close"
                        >
                            <span aria-hidden="true">&times;</span>
                        </button>
                        <h4 class="modal-title">表单</h4>
                    </div>
                    <div class="modal-body">
                        <!-- 修改表单 -->
                        <form class="form-horizontal">
                                <#list fieldList as field>
                                <div class="form-group">
                                     <#if field.name!="id" && field.nameHump!="createdAt" && field.nameHump!="updatedAt" && field.nameHump!="sort">
                                      <label class="col-sm-2 control-label">${field.nameCn}</label>
                                      <div class="col-sm-10">
                                          <input
                                                  type="text"
                                                  class="form-control"
                                                  placeholder="${field.nameCn}"
                                                  v-model="${domain}.${field.nameHump}"
                                          />
                                      </div>
                                 </#if>
                                </div>
                              </#list>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">
                            取消
                        </button>
                        <button type="button" class="btn btn-primary" v-on:click="update()">
                            保存
                        </button>
                    </div>
                </div>
            </div>
        </div>
        <!-- /.modal -->
    </div>
</template>

<script>
    import Pagination from "../../components/Pagination.vue";
    export default {
        components: { Pagination },
        name: "${domain}",
        //返回值
        data: function() {
            return {
                ${domain}: {},
                ${domain}s: [],
            };
        },
        mounted: function() {
            let _this = this;
            _this.$refs.pagination.size = 5;
            _this.list(1);
        },
        methods: {
            //添加${tableNameCn}打开模态框
            toadd() {
                let _this = this;
                _this.${domain} = {};
                $("#${domain}-add-model").modal("show");
            },
            //
            //添加${tableNameCn}打开模态框
            toupdate(${domain}) {
                let _this = this;
                //消除双向绑定，复制对象
                _this.${domain} = $.extend({}, ${domain});
                $("#${domain}-update-model").modal("show");
            },
            //${tableNameCn}列表
            list(page) {
                let _this = this;
                Loading.show();
                _this.$ajax
                    .post(
                        process.env.VUE_APP_SERVER+"/business/admin/${domain}/list",
                        //传参对象
                        {
                            pageSize: _this.$refs.pagination.size,
                            currentPage: page,
                        }
                    )
                    .then(
                        //响应结果
                        (response) => {
                            Loading.hide();
                            console.log("查询${tableNameCn}列表", response);
                            let resp = response.data;
                            _this.${domain}s = resp.data.records;
                            _this.$refs.pagination.render(page, resp.data.totalRecord);
                        }
                    );
            },
            //${tableNameCn}保存
            save() {
                let _this = this;
                // 保存校验
                if (1 != 1
                    <#list fieldList as field>
                    <#if field.name!="id" && field.nameHump!="createdAt" && field.nameHump!="updatedAt" && field.nameHump!="sort">
                    <#if !field.nullAble>
                    || !Validator.require(_this.${domain}.${field.nameHump}, "${field.nameCn}")
                    </#if>
                    <#if (field.length > 0)>
                    || !Validator.length(_this.${domain}.${field.nameHump}, "${field.nameCn}", 1, ${field.length?c})
                    </#if>
                    </#if>
                    </#list>
                ) {
                    return;
                }
                Loading.show();
                _this.$ajax
                    .post(
                        process.env.VUE_APP_SERVER+"/business/admin/${domain}/save",
                        //传参对象
                        _this.${domain}
                    )
                    .then(
                        //响应结果
                        (response) => {
                            console.log("保存${tableNameCn}成功", response);
                            let resp = response.data;
                            //保存成功
                            if (resp.success) {
                                //关闭模态框
                                $("#${domain}-add-model").modal("hide");
                                //刷新列表
                                _this.list(1);
                                ToastMin.success("保存成功！");
                            }else{
                                ToastMax.warning(resp.message);
                            }
                        }
                    );
            },
            //${tableNameCn}修改
            update() {
                let _this = this;
                _this.$ajax
                    .post(
                        process.env.VUE_APP_SERVER+"/business/admin/${domain}/update",
                        //传参对象
                        _this.${domain}
                    )
                    .then(
                        //响应结果
                        (response) => {
                            console.log("修改${tableNameCn}成功", response);
                            let resp = response.data;
                            //保存成功
                            if (resp.success) {
                                //关闭模态框
                                $("#${domain}-update-model").modal("hide");
                                //刷新列表
                                _this.list(1);
                                ToastMin.success("保存成功！");
                            }
                        }
                    );
            },
            //${tableNameCn}删除
            del(id) {
                let _this = this;
                //确认框
                Confirm.show("删除${tableNameCn}后将不可恢复！", function() {
                    //确认删除
                    _this.$ajax
                        .delete(process.env.VUE_APP_SERVER+"/business/admin/${domain}/delete/" + id)
                        .then(
                            //响应结果
                            (response) => {
                                console.log("删除${tableNameCn}成功", response);
                                let resp = response.data;
                                //保存成功
                                if (resp.success) {
                                    //刷新列表
                                    _this.list(1);
                                    ToastMin.success("删除成功！");
                                }
                            }
                        );
                });
            },
        },
    };
</script>
