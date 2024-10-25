import { data } from "autoprefixer";
import axios from "axios";

export const login = async (email, password) => {
  const { data } = await axios.post("http://localhost:8080/user/login", {
    email: email,
    password: password,
  });
  console.log("user login successful");
  return data;
};

export const signup = async (
  firstName,
  lastName,
  phoneNo,
  address,
  email,
  password
) => {
  const { data } = await axios.post("http://localhost:8080/user", {
    firstName: firstName,
    lastName: lastName,
    phone: phoneNo,
    address: address,
    email: email,
    password: password,
  });
  console.log("user signup successful");
  return data;
};
