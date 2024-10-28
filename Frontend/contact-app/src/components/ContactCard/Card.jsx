import React, { useState } from "react";
import { FaUserCircle } from "react-icons/fa";
import { MdDelete } from "react-icons/md";
import { FaEdit } from "react-icons/fa";
import DeleteModal from "../modals/DeleteModal";
import { deleteContact } from "../../service/ContactService";

const Card = (props) => {
  const [isModalOpen, setIsModalOpen] = useState(false);

  const openDeleteModal = () => setIsModalOpen(true);
  const closeDeleteModal = () => setIsModalOpen(false);

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
              <button>
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

            {isModalOpen && (
              <DeleteModal
                onClose={closeDeleteModal}
                deleteContact={props.onDelete}
              />
            )}
          </div>
        </div>
      </div>
    </div>
  );
};

export default Card;
