export default class StudentDto {
    id?: number
    name?: string
    istId?: string
    email?: string
    workflowStatus?: string
  
    constructor(obj?: Partial<StudentDto>) {
      Object.assign(this, obj)
    }
  }
  