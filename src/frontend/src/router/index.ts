import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import PeopleView from '../views/people/PeopleView.vue'
import StatisticsView from '../views/statistics/StatisticsView.vue'
import StudentsView from '../views/students/StudentsView.vue'
import StudentsDetailsView from '../views/students/StudentsDetailsView.vue'
import LogsView from '../views/logs/LogsView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView
    },
    {
      path: '/people',
      name: 'people',
      component: PeopleView
    },
    {
      path: '/students',
      name: 'students',
      component: StudentsView
    },
    {
      path: '/students/:id',
      name: 'student',
      component: StudentsDetailsView
    },
    {
      path: '/statistics',
      name: 'statistics',
      component: StatisticsView
    },
    {
      path: '/logs',
      name: 'logs',
      component: LogsView
    }
  ]
})

export default router
