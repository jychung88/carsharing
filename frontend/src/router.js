
import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router);


import ReservationManager from "./components/ReservationManager"

import RentalManager from "./components/RentalManager"

import PaymentManager from "./components/PaymentManager"

import CustomerManager from "./components/CustomerManager"

import MyPage from "./components/MyPage"
export default new Router({
    // mode: 'history',
    base: process.env.BASE_URL,
    routes: [
            {
                path: '/Reservation',
                name: 'ReservationManager',
                component: ReservationManager
            },

            {
                path: '/Rental',
                name: 'RentalManager',
                component: RentalManager
            },

            {
                path: '/Payment',
                name: 'PaymentManager',
                component: PaymentManager
            },

            {
                path: '/Customer',
                name: 'CustomerManager',
                component: CustomerManager
            },

            {
                path: '/MyPage',
                name: 'MyPage',
                component: MyPage
            },


    ]
})
