import axios from "axios";
import { API_URL } from "./Constants";

export const getContactsByUserId = async (userId, sortBy, search) => {
  const { data } = await axios.get(
    `http://localhost:8080/contact/user/${userId}`, {
      params: { search, sortBy },
    }
  );
  console.log("Data fetched successfully");
  return data;
};

export const createContact = async (
  firstName,
  lastName,
  email,
  phone,
  address,
  user_id
) => {
  const { data } = await axios.post(`http://localhost:8080/contact`, {
    firstName,
    lastName,
    email,
    phone,
    address,
    user_id,
  });
  return data;
};

export const deleteContact = async (contactId) => {
  const { data } = await axios.delete(
    `http://localhost:8080/contact/${contactId}`
  );
};

export const getContactById = async (contactId) => {
  const { data } = await axios.get(
    `http://localhost:8080/contact/${contactId}`
  );
  return data;
};

export const updateContact = async (
  contactId,
  firstName,
  lastName,
  email,
  phone,
  address,
  user_id
) => {
  const { data } = await axios.put(
    `http://localhost:8080/contact/${contactId}`,
    { firstName, lastName, email, phone, address, user_id }
  );
};

