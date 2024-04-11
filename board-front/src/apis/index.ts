import axios from 'axios';
import { SignInRequestDto, SignUpRequestDto } from './request/auth';
import { SignInResponseDto } from './response/auth';
import { ResponseDto } from './response';
import { access } from 'fs';
import { GetSignInUserResponseDto } from './response/user';

const DOMAIN = 'http://localhost:4000';

const API_DOMAIN =`${DOMAIN}/api/v1`;

const authorization = (accessToken:string) => {
    return {headers:{ Authorization: `Bearer ${accessToken}`}};
};

const SIGN_IN_URL = () => `${API_DOMAIN}/auth/sign-in`;
const SIGN_UP_URL = () => `${API_DOMAIN}/auth/sign-up`;

export const signInRequest = async (reqeustBody:SignInRequestDto) => {
    const result = await axios.post(SIGN_IN_URL(), reqeustBody)
        .then(response => {
            const responseBody:SignInResponseDto = response.data;
            return responseBody;
        })
        .catch(error => {
            if(!error.response || !error.response.data) return null;
            const responseBody:ResponseDto = error.response.data;
            return responseBody;
        });
    return result;
};

export const signUpRequest = async (reqeustBody:SignUpRequestDto) => {
    const result = await axios.post(SIGN_UP_URL(), reqeustBody)
        .then(response => {
            const responseBody:SignInResponseDto = response.data;
            return responseBody;
        })
        .catch(error => {
            if(!error.response || !error.response.data) return null;
            const responseBody:ResponseDto = error.response.data;
            return responseBody;
        });
    return result;
};

const GET_SIGN_IN_USER_URL = () => `${API_DOMAIN}/user`;

export const getSignInUserRequest = async (accessToken:string) => {
    const result = await axios.get(GET_SIGN_IN_USER_URL(), authorization(accessToken))
        .then(response => {
            const responseBody:GetSignInUserResponseDto = response.data;
            return responseBody;
        })
        .catch(error => {
            if(!error.response || !error.response.data) return null;
            const responseBody:ResponseDto = error.response.data;
            return responseBody;
        });
    return result;
}