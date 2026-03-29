<template>
  <el-dialog title="修改" width="500px" :close-on-click-modal="false" :close-on-press-escape="false" v-model="dialogVisible" :before-close="handleClose">
    <el-form ref="formRef" :model="form" :rules="rules" label-width="100px" class="tams-form-container">
      <el-form-item label="名称" prop="name">
        <el-input v-model="form.name" class="tams-form-item" placeholder="请输入教室名称" />
      </el-form-item>
      <el-form-item label="类型" prop="type">
        <el-select v-model="form.type" class="tams-form-item" placeholder="请选择教室类型">
          <el-option label="普通教室" :value="1" />
          <el-option label="多媒体教室" :value="2" />
          <el-option label="实验室" :value="3" />
          <el-option label="操场" :value="4" />
        </el-select>
      </el-form-item>
      <el-form-item label="容量" prop="capacity">
        <el-input-number v-model="form.capacity" :min="1" :max="500" class="tams-form-item" placeholder="请输入容量" />
      </el-form-item>
      <el-form-item label="可用时间段" prop="availableTime">
        <el-select v-model="form.availableTime" class="tams-form-item" placeholder="请选择可用时间段">
          <el-option label="06:00-08:00" value="06:00-08:00" />
          <el-option label="08:00-10:00" value="08:00-10:00" />
          <el-option label="10:00-12:00" value="10:00-12:00" />
          <el-option label="12:00-14:00" value="12:00-14:00" />
          <el-option label="14:00-16:00" value="14:00-16:00" />
          <el-option label="16:00-18:00" value="16:00-18:00" />
          <el-option label="18:00-20:00" value="18:00-20:00" />
          <el-option label="20:00-22:00" value="20:00-22:00" />
        </el-select>
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
import { useClassroomStore } from '@/stores/classroom'

const props = defineProps<{ visible: boolean; id: number | string }>()
const emit = defineEmits<{ 'on-close': []; 'on-success': [] }>()

const classroomStore = useClassroomStore()

const dialogVisible = ref(false)
const formRef = ref<FormInstance>()
const form = ref<any>({})
const submitBtnLoading = ref(false)

const rules: FormRules = {
  name: [{ required: true, message: '名称不能为空', trigger: 'blur' }],
  type: [{ required: true, message: '类型不能为空', trigger: 'change' }],
  capacity: [{ required: true, message: '容量不能为空', trigger: 'change' }]
}

const search = () => {
  classroomStore.getClassroomById(Number(props.id)).then((res: any) => {
    if (res) form.value = res
  }).catch(() => {})
}

const resetData = () => {
  formRef.value?.resetFields()
  form.value = {}
}

const handleClose = (done: () => void) => {
  resetData()
  emit('on-close')
  done()
}

const close = () => {
  resetData()
  emit('on-close')
  dialogVisible.value = false
}

const submit = () => {
  formRef.value?.validate((valid) => {
    if (valid) {
      submitBtnLoading.value = true
      classroomStore.updateClassroomById(Number(props.id), form.value).then(() => {
        submitBtnLoading.value = false
        resetData()
        emit('on-success')
        dialogVisible.value = false
      }).catch(() => {
        submitBtnLoading.value = false
      })
    }
  })
}

watch(() => props.visible, (val) => {
  if (val) {
    search()
    dialogVisible.value = val
  }
})
</script>
