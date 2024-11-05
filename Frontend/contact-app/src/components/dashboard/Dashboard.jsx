import { useEffect, useState } from "react";
import Button from "../button/Button";
import { getContactsByUserId } from "../../service/ContactService";
import Card from "../ContactCard/Card";
import AddContactModal from "../modals/AddContactModal";
import { useSearchParams } from "react-router-dom";
import { useDebouncedValue } from "../../Hooks/useDebouncedSearch";
import Pagination from "../pagination/Pagination";

export default function Dashboard() {
  const [contact, setContact] = useState([]);
  const [isAddContactModalOpen, setIsAddContactModalOpen] = useState(false);
  const [searchParams, setSearchParams] = useSearchParams();
  const sortBy = searchParams.get("sortBy") || '';
  const searchBy = searchParams.get("search") || "";
  const [filter, setFilter] = useState(sortBy);
  const debouncedSearchTerm = useDebouncedValue(searchParams, 2000);

  const openAddContactModal = () => setIsAddContactModalOpen(true);
  const closeAddContactModal = () => setIsAddContactModalOpen(false);

  const handleFilterChange = async (event) => {
    const filterBy = event.target.value;
    setFilter(filterBy);

    setSearchParams((prevParams) => {
      const newParams = new URLSearchParams(prevParams);
      newParams.set("sortBy", filterBy);
      return newParams;
    });
    fetchContacts(filterBy)
  };

  const userDetails = localStorage.getItem("userData");
  const currentUser = JSON.parse(userDetails);

  const fetchContacts = async (sortBy, searchBy) => {
    const data = (await getContactsByUserId(currentUser.id, sortBy, searchBy)) || [];
    setContact(data);
    console.log(data);
  };

  useEffect(() => {
    fetchContacts(sortBy, searchBy);
  }, [debouncedSearchTerm]);

  return (
    <div>
      <div className="flex justify-end mr-6 mt-1">
        <Button
          name="Add Contact"
          className="bg-blue-600 ml-2 my-3 px-5 py-2 flex justify-center items-center transition duration-500 ease-in-out hover:bg-blue-500 rounded-2xl font-semibold text-sm"
          onClick={openAddContactModal}
        />
        <select defaultValue="" value={filter} onChange={handleFilterChange}>
          <option value="" disabled hidden>
            Filter
          </option>
          <option value="A-Z">From A-Z</option>
          <option value="Z-A">From Z-A</option>
          <option value="oldDate">Date Old</option>
          <option value="newDate">Date New</option>
        </select>
      </div>

      <div>
        <h1 className="text-center font-bold text-3xl text-cyan-950">
          MY CONTACTS
        </h1>
        <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-6 p-6">
          {contact.map((value) => {
            return (
              <Card
                userId={value.user.id}
                firstName={value.firstName}
                lastName={value.lastName}
                phone={value.phone}
                email={value.email}
                address={value.address}
                contactId={value.id}
                fetchContacts={() => fetchContacts()}
              />
            );
          })}
        </div>
      </div>

      {isAddContactModalOpen && (
        <AddContactModal
          onClose={closeAddContactModal}
          getContacts={fetchContacts}
          userId={currentUser.id}
        />
      )}

      <Pagination/>
    </div>
  );
}
