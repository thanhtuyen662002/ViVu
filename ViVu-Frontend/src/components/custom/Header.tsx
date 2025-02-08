import React, { useState, useEffect, useRef } from "react";
import { Label } from "@/components/ui/label";
import { Search, MapPin, Sparkles } from "lucide-react";
import { useLanguage } from "@/contexts/LanguageContext";
import VietnamFlag from "@/assets/vietnam.png";
import UnitedKingdom from "@/assets/united-kingdom.png";
import Logo from "@/assets/LogoWithName.png";
import { Input } from "../ui/input";
import { Button } from "../ui/button";
import axios from "axios";
import { API_URL, API_ENDPOINTS } from "@/config/APIConfig";
import { useNavigate } from "react-router";

export default function Header() {
  const { t, setLanguage, language } = useLanguage();
  const navigate = useNavigate();
  const [locations, setLocations] = useState<string[]>([]); // Danh sách địa điểm từ server
  const [filteredLocations, setFilteredLocations] = useState<string[]>([]); // Danh sách gợi ý
  const [searchValue, setSearchValue] = useState<string>(""); // Giá trị input
  const [isDropdownVisible, setIsDropdownVisible] = useState<boolean>(false); // Trạng thái dropdown
  const dropdownRef = useRef<HTMLDivElement>(null); // Ref để kiểm tra click bên ngoài

  // Gọi API khi load trang
  useEffect(() => {
    async function fetchLocations() {
      try {
        const response = await axios.get(
          API_URL + API_ENDPOINTS.getAllLocations
        );
        setLocations(response.data);
      } catch (error) {
        console.error("Error fetching locations:", error);
      }
    }

    fetchLocations();
  }, []);

  // Lọc danh sách địa điểm khi nhập vào input
  const handleInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const value = event.target.value;
    setSearchValue(value);
    setIsDropdownVisible(true);
    if (value === "") {
      setIsDropdownVisible(false);
    }

    // Lọc địa điểm gần giống với ký tự nhập vào
    const filtered = locations.filter((location) =>
      location.toLowerCase().includes(value.toLowerCase())
    );

    setFilteredLocations(filtered);
    setIsDropdownVisible(true); // Hiển thị dropdown khi nhập
  };

  // Xử lý khi click vào một địa điểm
  const handleLocationClick = (location: string) => {
    setSearchValue(location); // Điền địa điểm vào ô input
    setIsDropdownVisible(false); // Ẩn dropdown
  };

  // Ẩn dropdown khi click ra ngoài
  const handleClickOutside = (event: MouseEvent) => {
    if (
      dropdownRef.current &&
      !dropdownRef.current.contains(event.target as Node)
    ) {
      setIsDropdownVisible(false); // Ẩn dropdown nếu click ngoài dropdown
    }
  };

  // Lắng nghe sự kiện click bên ngoài
  useEffect(() => {
    document.addEventListener("mousedown", handleClickOutside);
    return () => {
      document.removeEventListener("mousedown", handleClickOutside);
    };
  }, []);

  const handleSearchClick = async () => {
      navigate("/results", { state: { searchValue: searchValue } });
  };

  return (
    <header className="fixed top-0 z-50 flex h-16 w-full items-center justify-between border-b bg-background px-4 shadow-sm md:px-6">
      <div className="flex items-center gap-4">
        <div className="w-32 flex items-center">
          <img src={Logo} className="h-14" />
        </div>
      </div>

      {/* Search bar với dropdown */}
      <div className="relative flex w-1/3 group focus-within:ring-2 focus-within:ring-primary rounded-full">
        <Input
          id="search"
          placeholder={t("input")}
          value={searchValue}
          onChange={handleInputChange}
          autoComplete="off"
          className="rounded-full rounded-r-none border-r-0 focus-visible:outline-none"
        />
        <Button
          variant={"outline"}
          className="shadow-none rounded-full rounded-l-none"
          onClick={handleSearchClick}
        >
          <Search />
        </Button>

        {/* Dropdown hiển thị danh sách gợi ý */}
        {isDropdownVisible && (
          <div
            ref={dropdownRef}
            className="absolute top-12 left-0 w-full bg-white border border-gray-200 rounded-lg shadow-lg z-10"
          >
            {filteredLocations.length > 0 ? (
              filteredLocations.map((location, index) => (
                <div
                  key={index}
                  onClick={() => handleLocationClick(location)} // Xử lý khi click
                  className="flex items-center px-4 py-2 hover:bg-gray-100 cursor-pointer"
                >
                  <MapPin className="w-5 h-5 text-primary mr-2" />
                  <span>{location}</span>
                </div>
              ))
            ) : (
              <div
                className="flex items-center px-4 py-2 text-gray-500 cursor-pointer"
                onClick={() => handleLocationClick(searchValue)}
              >
                <Sparkles className="w-5 h-5 text-primary mr-2" />
                <span>{searchValue}</span>
              </div>
            )}
          </div>
        )}
      </div>

      <div className="flex items-center gap-4">
        <div className="flex justify-center align-center">
          <div className="w-24"></div>
          <Label
            htmlFor="language-mode"
            onClick={() => setLanguage(language === "vi" ? "en" : "vi")}
            className="cursor-pointer"
          >
            <img
              src={language === "vi" ? VietnamFlag : UnitedKingdom}
              className="h-10 w-10"
            />
          </Label>
        </div>
      </div>
    </header>
  );
}
