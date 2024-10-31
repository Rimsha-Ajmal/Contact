import React, { useState } from "react";
import { FaUserCircle } from "react-icons/fa";
import { MdDelete } from "react-icons/md";
import { FaEdit } from "react-icons/fa";
import DeleteModal from "../modals/DeleteModal";
import { deleteContact, getContactById } from "../../service/ContactService";
import EditModal from "../modals/EditModal";

const Card = (props) => {
  const [isDeleteModalOpen, setIsDeleteModalOpen] = useState(false);
  const [isEditModalOpen, setIsEditModalOpen] = useState(false);
  const [currentContactData, setCurrentContactData] = useState();

  const openDeleteModal = () => setIsDeleteModalOpen(true);
  const closeDeleteModal = () => setIsDeleteModalOpen(false);

  const openEditModal = () => setIsEditModalOpen(true);
  const closeEditModal = () => {
    setIsEditModalOpen(false);
    setCurrentContactData(undefined);
  };
  
  const getContactByContactId = async () => {
    const data = await getContactById(props.contactId);
    setCurrentContactData(data);
  };

  return (
    <div className="flex justify-center my-4">
      <div className="bg-cyan-950 w-full max-w-md rounded-3xl shadow-lg overflow-hidden">
        <div className="bg-cyan-700 p-6 flex justify-center">
          <FaUserCircle className="text-6xl text-white" />
        </div>
        <div className="p-6 text-white">
          <div className="flex">
            <div className="flex justify-center items-center w-full">
              <h1 className="text-2xl font-bold text-white mb-4">
                {props.firstName} {props.lastName}
              </h1>
            </div>
            <div className="flex gap-1 items-center">
              <button onClick={openDeleteModal}>
                <MdDelete className="text-2xl" />
              </button>
              <button
                onClick={() => {
                  getContactByContactId();
                  openEditModal();
                }}
              >
                <FaEdit className="text-2xl" />
              </button>
            </div>
          </div>
          <div className="space-y-3">
            <p>
              <span className="font-bold">Phone:</span> {props.phone}
            </p>
            <p>
              <span className="font-bold">Email:</span> {props.email}
            </p>
            <p>
              <span className="font-bold">Address:</span> {props.address}
            </p>

            {isDeleteModalOpen && (
              <DeleteModal
                onClose={closeDeleteModal}
                contactId={props.contactId}
                getDeletedContacts={props.fetchContacts}
              />
            )}

            {isEditModalOpen && currentContactData && (
              <EditModal
                onClose={closeEditModal}
                contactData={currentContactData}
                getContacts={props.fetchContacts}
              />
            )}
          </div>
        </div>
      </div>
    </div>
  );
};

export default Card;
