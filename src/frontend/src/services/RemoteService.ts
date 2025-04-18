import axios from 'axios'
import type { AxiosResponse } from 'axios'
import { useAppearanceStore } from '@/stores/appearance'
import DeiError from '@/models/DeiError'
import type PersonDto from '@/models/PersonDto'
import type StudentDto from '@/models/StudentDto'

const httpClient = axios.create()
httpClient.defaults.timeout = 50000
httpClient.defaults.baseURL = import.meta.env.VITE_ROOT_API
httpClient.defaults.headers.post['Content-Type'] = 'application/json'

export default class RemoteServices {
  static async getPeople(): Promise<PersonDto[]> {
    return httpClient.get('/people')
  }

  static async getStudents(): Promise<StudentDto[]> {
    return httpClient.get('/students')
  }

  static async getStudentById(id: string | number): Promise<StudentDto> {
    return httpClient.get(`/people/${id}`)
  }
  
  static async getTeachers(): Promise<PersonDto[]> {
    return httpClient.get('/teachers')
  }

  static async getStatistics(): Promise <any> {
    return httpClient.get('/statistics')
  }

  static async createPerson(person: PersonDto): Promise<PersonDto> {
    return httpClient.post('/people', person)
  }

  static async updatePerson(person: PersonDto): Promise<PersonDto> {  
    const response = await httpClient.put<PersonDto>(`/people/${person.id}`, person);
    return response.data;
  } 

  static async deletePerson(person: PersonDto): Promise<AxiosResponse> {
    return httpClient.delete(`/people/${person.id}`)
  }

  static async updateThesisWorkflowState(id: number, state: string) {
    return httpClient.patch(`/${id}/thesis-state/${state}`);
}

  static async updateDefenseWorkflowState(id: number, state: string) {
    return httpClient.patch(`/${id}/defense-state/${state}`);
  }

  static async getLogs(): Promise<any> {
    return httpClient.get('/logs');
  }

  static async errorMessage(error: any): Promise<string> {
    if (error.message === 'Network Error') {
      return 'Unable to connect to the server'
    } else if (error.message.split(' ')[0] === 'timeout') {
      return 'Request timeout - Server took too long to respond'
    } else {
      return error.response?.data?.message ?? 'Unknown Error'
    }
  }

  static async handleError(error: any): Promise<never> {  
    const deiErr = new DeiError(
      await RemoteServices.errorMessage(error),
      error.response?.data?.code ?? -1
    )
    const appearance = useAppearanceStore()
    appearance.pushError(deiErr)
    appearance.loading = false
    throw deiErr
  }  
}

httpClient.interceptors.request.use((request) => request, RemoteServices.handleError)
httpClient.interceptors.response.use((response) => response.data, RemoteServices.handleError)
