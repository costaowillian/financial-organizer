import React, { createContext, useEffect, useState } from "react";
import api from "../services/api";
import { jwtDecode } from "jwt-decode";


const Context = createContext();

const AuthProvider = ({children}) => {

    const [isAuthenticated, setIsAuthenticated] = useState(false);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const data = localStorage.getItem('user_data');
        if(data && JSON.parse(data).accessToken && isTokenValid(JSON.parse(data).accessToken)) {
            setIsAuthenticated(true);
        } else {
            setIsAuthenticated(false);
        }
        setLoading(false);
    }, []);

    const login = async (credentials) => {
        try {
            const response = await api.post('/auth/signin', credentials);

            if(response) {
                localStorage.setItem('user_data', JSON.stringify(response.data));
                setIsAuthenticated(true);
            }  
        } catch (error) {
            throw error;
        }
    }

    const logout = () => {
        localStorage.removeItem('user_data');
        setIsAuthenticated(false);
    }

    const isTokenValid = (token) => {    
        try {
            const decodedToken = jwtDecode(token);
            const currentTime = Date.now() / 1000; 
            
            if (decodedToken.exp && decodedToken.exp < currentTime) {
                return false; 
            }
            return true;
        } catch (error) {
            return false;
        }
    }

    return (
        <Context.Provider value={{ isAuthenticated, login, loading, logout, isTokenValid }}>
            {children}
        </Context.Provider>
    )
}

export { Context, AuthProvider };