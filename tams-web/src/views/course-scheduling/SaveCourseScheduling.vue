<template>
  <el-dialog title="新增" width="790px" :close-on-click-modal="true" :close-on-press-escape="true" v-model="dialogVisible" :before-close="handleClose">
    <el-form ref="formRef" :model="form" :rules="rules" inline label-width="80px" class="tams-form-container">
      <el-form-item label="日期" prop="date">
        <el-date-picker v-model="form.date" type="date" value-format="YYYY-MM-DD" :clearable="false" class="tams-form-item" />
      </el-form-item>
      <br />
      <el-form-item label="班级" prop="classIdList">
        <el-select v-model="form.classIdList" multiple class="tams-form-item">
          <el-option v-for="item in classData" :key="item.id" :label="item.name" :value="item.id" />
        </el-select>
      </el-form-item>
      <br />
      <el-form-item label="教室" prop="classroomId">
        <el-select v-model="form.classroomId" class="tams-form-item">
          <el-option v-for="item in classroomData" :key="item.id" :label="item.name" :value="item.id" />
        </el-select>
      </el-form-item>
      <br />
      <el-form-item label="课程" prop="courseId">
        <el-select v-model="currentCourse" value-key="id" class="tams-form-item" @change="courseChange">
          <el-option v-for="item in courseData" :key="item.id" :label="item.name" :value="item" />
        </el-select>
      </el-form-item>
      <el-form-item label="老师" prop="teacherId">
        <el-select v-model="form.teacherId" class="tams-form-item">
          <el-option v-for="item in teacherData" :key="item.id" :label="item.name" :value="item.id" />
        </el-select>
      </el-form-item>
      <el-form-item label="时间" prop="attendTime">
        <el-time-select
          :clearable="false"
          style="width: 210px;"
          v-model="form.attendTime"
          :picker-options="pickerOptions"
          @change="calcFinishTime"
        />
      </el-form-item>
      <el-form-item>
        <el-input-number
          v-model="courseDuration"
          style="width: 150px;"
          :step="CONSTS.COURSE_DURATION_STEP_MINUTE"
          :min="CONSTS.COURSE_DURATION_STEP_MINUTE"
          :max="360"
          @change="calcFinishTime"
        />
      </el-form-item>
      <el-form-item prop="finishTime">
        <el-time-select
          :clearable="false"
          style="width: 210px;"
          v-model="form.finishTime"
          :picker-options="finishPickerOptions"
          @change="calcCourseDuration"
        />
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
import { computed, ref, watch } from 'vue'
import type { FormInstance, FormRules } from 'element-plus'
import dayjs from 'dayjs'
import { useClassroomStore } from '@/stores/classroom'
import { useCourseStore } from '@/stores/course'
import { useTeacherStore } from '@/stores/teacher'
import { useCourseSchedulingStore } from '@/stores/courseScheduling'
import { useClazzStore } from '@/stores/clazz'
import { CONSTS } from '@/utils/consts'

const props = defineProps<{
  visible: boolean
  date?: string
  classroomId?: number | string
}>()
const emit = defineEmits<{ 'on-close': []; 'on-success': [] }>()

const classroomStore = useClassroomStore()
const courseStore = useCourseStore()
const teacherStore = useTeacherStore()
const csStore = useCourseSchedulingStore()
const clazzStore = useClazzStore()

const dialogVisible = ref(false)
const formRef = ref<FormInstance>()
const form = ref<any>({})
const classroomData = ref<any[]>([])
const courseData = ref<any[]>([])
const teacherData = ref<any[]>([])
const currentCourse = ref<any>(null)
const classData = ref<any[]>([])
const courseDuration = ref(CONSTS.COURSE_DURATION_STEP_MINUTE)
const submitBtnLoading = ref(false)

const pickerOptions = {
  start: '07:00',
  step: '00:' + CONSTS.COURSE_DURATION_STEP_MINUTE,
  end: '20:00'
}

const finishPickerOptions = computed(() => ({
  start: form.value.attendTime || '07:00',
  step: '00:' + CONSTS.COURSE_DURATION_STEP_MINUTE,
  end: '23:00',
  minTime: form.value.attendTime || '07:00'
}))

const rules: FormRules = {
  date: [{ required: true, message: '日期不能为空', trigger: 'blur' }],
  classroomId: [{ required: true, message: '教室不能为空', trigger: 'blur' }],
  courseId: [{ required: true, message: '课程不能为空', trigger: 'blur' }],
  teacherId: [{ required: true, message: '老师不能为空', trigger: 'blur' }],
  attendTime: [{ required: true, message: '上课时间不能为空', trigger: 'blur' }],
  finishTime: [{ required: true, message: '下课时间不能为空', trigger: 'blur' }],
  classIdList: [{ required: true, type: 'array', min: 1, message: '班级不能为空', trigger: 'blur' }]
}

const init = async () => {
  form.value = {
    date: props.date || undefined,
    classroomId: props.classroomId ? Number(props.classroomId) : undefined,
    classIdList: [],
    courseId: undefined,
    teacherId: undefined,
    attendTime: '',
    finishTime: ''
  }
  currentCourse.value = null
  courseDuration.value = CONSTS.COURSE_DURATION_STEP_MINUTE

  const [classroomList, courseList, teacherList, clazzList] = await Promise.all([
    classroomStore.getClassroomRefList().catch(() => []),
    courseStore.getCourseRefList().catch(() => []),
    teacherStore.getTeacherRefList().catch(() => []),
    clazzStore.getClazzList().catch(() => [])
  ])

  classroomData.value = classroomList || []
  courseData.value = courseList || []
  teacherData.value = teacherList || []
  classData.value = clazzList || []
}

const resetData = () => {
  formRef.value?.resetFields()
  form.value = {}
  courseDuration.value = CONSTS.COURSE_DURATION_STEP_MINUTE
  currentCourse.value = null
  classroomData.value = []
  courseData.value = []
  teacherData.value = []
  classData.value = []
}

const handleClose = (done: () => void) => { resetData(); emit('on-close'); done() }
const close = () => { resetData(); emit('on-close'); dialogVisible.value = false }

const submit = () => {
  formRef.value?.validate((valid) => {
    if (valid) {
      submitBtnLoading.value = true
      form.value.attendTime = dayjs(form.value.attendTime, 'HH:mm').format('HH:mm:ss')
      form.value.finishTime = dayjs(form.value.finishTime, 'HH:mm').format('HH:mm:ss')
      csStore.saveCourseScheduling(form.value).then(() => {
        submitBtnLoading.value = false
        resetData()
        emit('on-success')
        dialogVisible.value = false
      }).catch(() => {
        form.value.attendTime = dayjs(form.value.attendTime, 'HH:mm:ss').format('HH:mm')
        form.value.finishTime = dayjs(form.value.finishTime, 'HH:mm:ss').format('HH:mm')
        submitBtnLoading.value = false
      })
    }
  })
}

const courseChange = (val: any) => {
  form.value.courseId = val?.id
  if (val?.duration && val.duration > 0) {
    courseDuration.value = val.duration
  }
  calcFinishTime()
}

const calcFinishTime = () => {
  if (form.value.attendTime && courseDuration.value > 0) {
    form.value.finishTime = dayjs(form.value.attendTime, 'HH:mm').add(courseDuration.value, 'minute').format('HH:mm')
  }
}

const calcCourseDuration = () => {
  if (!form.value.attendTime || !form.value.finishTime) {
    return
  }

  const start = dayjs(form.value.attendTime, 'HH:mm')
  const end = dayjs(form.value.finishTime, 'HH:mm')
  const diff = end.diff(start, 'minute')

  if (diff > 0) {
    courseDuration.value = diff
  }
}

watch(() => props.visible, (val) => {
  if (val) {
    init()
    dialogVisible.value = val
  }
})
</script>
