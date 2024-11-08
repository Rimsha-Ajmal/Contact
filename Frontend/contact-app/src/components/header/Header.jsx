import {
  Disclosure,
  DisclosureButton,
  DisclosurePanel,
  Menu,
  MenuButton,
  MenuItem,
  MenuItems,
} from "@headlessui/react";
import { Bars3Icon, BellIcon, XMarkIcon } from "@heroicons/react/24/outline";
import { Outlet, useLocation, useNavigate } from "react-router";
import { Link, useSearchParams } from "react-router-dom";
import {
  MagnifyingGlassIcon,
  UserCircleIcon,
} from "@heroicons/react/24/outline";
import { useRef, useState } from "react";

const navigation = [{ name: "Dashboard", href: "/", current: true }];

function classNames(...classes) {
  return classes.filter(Boolean).join(" ");
}

export default function Header() {
  const navigate = useNavigate();
  const location = useLocation();
  const inputRef = useRef();
  const [searchParams, setSearchParams] = useSearchParams();
  const searchBy = searchParams.get("search") || "";
  const [searchInput, setSearchInput] = useState(searchBy);

  const handleSearchInputChange = (event) => {
    const search = event.target.value;
    setSearchInput(search);

    setSearchParams((prevParams) => {
      const newParams = new URLSearchParams(prevParams);
      newParams.set("search", search);
      return newParams;
    });
  };

  const logout = () => {
    localStorage.removeItem("userData");
    navigate("/login");
  };

  const focusInput = () => {
    inputRef.current?.focus();
  };

  return (
    <div>
      <Disclosure as="nav" className="bg-gray-800">
        <div className="mx-auto max-w-7xl px-2 sm:px-6 lg:px-8">
          <div className="relative flex h-16 items-center justify-between">
            <div className="absolute inset-y-0 left-0 flex items-center sm:hidden">
              {/* Mobile menu button*/}
              <DisclosureButton className="group relative inline-flex items-center justify-center rounded-md p-2 text-gray-400 hover:bg-gray-700 hover:text-white focus:outline-none focus:ring-2 focus:ring-inset focus:ring-white">
                <span className="absolute -inset-0.5" />
                <span className="sr-only">Open main menu</span>
                <Bars3Icon
                  aria-hidden="true"
                  className="block h-6 w-6 group-data-[open]:hidden"
                />
                <XMarkIcon
                  aria-hidden="true"
                  className="hidden h-6 w-6 group-data-[open]:block"
                />
              </DisclosureButton>
            </div>
            <div className="flex flex-1 items-center justify-center sm:items-stretch sm:justify-start">
              <div className="flex flex-shrink-0 items-center">
                <img
                  alt="Your Company"
                  src="https://tailwindui.com/plus/img/logos/mark.svg?color=indigo&shade=500"
                  className="h-8 w-auto"
                />
              </div>
              <div className="hidden sm:ml-6 sm:block">
                <div className="flex space-x-4">
                  <Link
                    to="/"
                    aria-current={
                      location.pathname === "/" ? "page" : undefined
                    }
                    className={classNames(
                      location.pathname === "/"
                        ? "bg-gray-900 text-white"
                        : "text-gray-300 hover:bg-gray-700 hover:text-white",
                      "rounded-md px-3 py-2 text-sm font-medium"
                    )}
                  >
                    Dashboard
                  </Link>
                </div>
              </div>
            </div>

            <div class="flex justify-center items-center w-full">
              <div className="relative flex items-center">
                <input
                  value={searchInput}
                  onChange={handleSearchInputChange}
                  ref={inputRef}
                  type="text"
                  placeholder="Search contacts"
                  className="pl-10 pr-4 py-1 border rounded-md w-80 bg-slate-700 border-slate-600  focus:border-slate-500 focus:outline-none"
                />
                <MagnifyingGlassIcon
                  onClick={focusInput}
                  aria-hidden="true"
                  className="absolute left-2 h-5 w-8 text-gray-600 cursor-pointer"
                />
              </div>
            </div>

            <div className="absolute inset-y-0 right-0 flex items-center pr-2 sm:static sm:inset-auto sm:ml-6 sm:pr-0">
              {/* Profile dropdown */}
              <Menu as="div" className="relative ml-3">
                <div>
                  <MenuButton className="relative flex rounded-full bg-gray-800 text-sm focus:outline-none  focus:ring-offset-2 focus:ring-offset-gray-800">
                    <span className="absolute -inset-1.5" />
                    <span className="sr-only">Open user menu</span>
                    <UserCircleIcon className="h-8 w-8 rounded-full text-white" />
                  </MenuButton>
                </div>
                <MenuItems
                  transition
                  className="absolute right-0 z-10 mt-2 w-48 origin-top-right rounded-md bg-white py-1 shadow-lg ring-1 ring-black ring-opacity-5 transition focus:outline-none data-[closed]:scale-95 data-[closed]:transform data-[closed]:opacity-0 data-[enter]:duration-100 data-[leave]:duration-75 data-[enter]:ease-out data-[leave]:ease-in"
                >
                  <MenuItem>
                    <Link
                      to="user-profile"
                      className="block px-4 py-2 text-sm text-gray-700 data-[focus]:bg-gray-100 data-[focus]:outline-none"
                    >
                      Your Profile
                    </Link>
                  </MenuItem>
                  <MenuItem>
                    <button
                      onClick={logout}
                      className="flex w-full px-4 py-2 text-sm text-gray-700 data-[focus]:bg-gray-100 data-[focus]:outline-none"
                    >
                      Sign out
                    </button>
                  </MenuItem>
                </MenuItems>
              </Menu>
            </div>
          </div>
        </div>

        <DisclosurePanel className="sm:hidden">
          <div className="space-y-1 px-2 pb-3 pt-2">
            <Link
              to="/"
              aria-current={location.pathname === "/" ? "page" : undefined}
              className={classNames(
                location.pathname === "/"
                  ? "bg-gray-900 text-white"
                  : "text-gray-300 hover:bg-gray-700 hover:text-white",
                "rounded-md px-3 py-2 text-sm font-medium"
              )}
            >
              Dashboard
            </Link>
          </div>
        </DisclosurePanel>
      </Disclosure>
      <Outlet />
    </div>
  );
}
