export default interface SignUpRequestDto{
    email:string;
    password: string;
    nickname: string;
    telNumber: string;
    address: string;
    adresssDetail: string|null;
    agreedPersonal: boolean;
}