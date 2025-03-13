export default class StudentDto {
    id?: number
    name?: string
    istId?: string
    email?: string
    phoneNumber?: string
    thesisWorkflowState?: string
    defenseWorkflowState?: string
  
    constructor(obj?: Partial<StudentDto>) {
      Object.assign(this, obj)
    }
  }
  