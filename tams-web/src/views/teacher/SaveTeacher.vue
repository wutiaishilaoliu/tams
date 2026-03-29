<template>
  <el-dialog title="新增" width="400px" :close-on-click-modal="false" :close-on-press-escape="false" v-model="dialogVisible" :before-close="handleClose">
    <el-form ref="formRef" :model="form" :rules="rules" label-width="80px" class="tams-form-container">
      <el-form-item label="姓名" prop="name">
        <el-input v-model="form.name" class="tams-form-item" placeholder="请输入姓名" />
      </el-form-item>
      <el-form-item label="登录账号" prop="username">
        <el-input v-model="form.username" class="tams-form-item" placeholder="请输入登录账号" />
      </el-form-item>
      <el-form-item label="登录密码" prop="password">
        <el-input v-model="form.password" class="tams-form-item" type="password" placeholder="不填则默认为123456" show-password />
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="close">取消</el-button>
        <el-button type="primary" :loading="submitBtnLoading" @click="submit">确定</el-button>
      </div>
    </template>
  </el-dialog>
</template>

<script setup lang="ts">
import { ref, watch } from 'vue'
import type { FormInstance, FormRules } from 'element-plus'
import { useTeacherStore } from '@/stores/teacher'

const props = defineProps<{ visible: boolean }>()
const emit = defineEmits<{ 'on-close': []; 'on-success': [] }>()

const teacherStore = useTeacherStore()
const dialogVisible = ref(false)
const formRef = ref<FormInstance>()
const form = ref<any>({})
const submitBtnLoading = ref(false)
const rules: FormRules = {
  name: [{ required: true, message: '姓名不能为空', trigger: 'blur' }],
  username: [{ required: true, message: '登录账号不能为空', trigger: 'blur' }]
}

const resetData = () => { formRef.value?.resetFields(); form.value = {} }
const handleClose = (done: () => void) => { resetData(); emit('on-close'); done() }
const close = () => { resetData(); emit('on-close'); dialogVisible.value = false }
const submit = () => {
  formRef.value?.validate((valid) => {
    if (valid) {
      submitBtnLoading.value = true
      teacherStore.saveTeacher(form.value).then(() => {
        submitBtnLoading.value = false; resetData(); emit('on-success'); dialogVisible.value = false
      }).catch(() => { submitBtnLoading.value = false })
    }
  })
}
watch(() => props.visible, (val) => { if (val) dialogVisible.value = val })
</script>
