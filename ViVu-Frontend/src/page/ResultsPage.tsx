import {
  LocationInfoCard,
  Locations,
} from "@/components/custom/LocationInforCard";
import HotelsCarousel from "@/components/custom/HotelsCarousel";
import PlacesCarousel from "@/components/custom/PlacesCarousel";
import EateryCarousel from "@/components/custom/EateryCarousel";
import FoodsCarousel from "@/components/custom/FoodsCarousel";
import { useEffect, useState } from "react";
import { useLanguage } from "@/contexts/LanguageContext";
import axios from "axios";
import { API_ENDPOINTS, API_URL } from "@/config/APIConfig";
import { useLocation } from "react-router-dom";

export default function ResultsPage() {
  const [location, setLocation] = useState<Locations | null>(null);
  const [places, setPlaces] = useState([]);
  const [hotels, setHotels] = useState([]);
  const [eateries, setEateries] = useState([]);
  const [localFoods, setLocalFoods] = useState([]);
  const locationState = useLocation();
  const searchValue = locationState.state?.searchValue;
  const [loading, setLoading] = useState<boolean>(false);
  const [error, setError] = useState<string | null>(null);
  const { t } = useLanguage();

  useEffect(() => {
    const axiosInstance = axios.create({
      baseURL: API_URL,
      timeout: 300000, // 5 phút (300,000ms)
    });

    const fetchData = async () => {
      setLoading(true);
      setError(null);
      try {
        const response = await axiosInstance.get(
          `${API_ENDPOINTS.getData}${searchValue}`
        );
        const data = response.data;

        if (typeof window !== "undefined") {
          window.localStorage.setItem("searchResults", JSON.stringify(data));
          window.localStorage.setItem("searchStatus", "true");
        }
      }  catch (error) {
        setError("Error fetching search results. Please try again later.");
        console.error("Error fetching search results:", error);
      } finally {
        setLoading(false);
      }

      const storedData = localStorage.getItem("searchResults");
      if (storedData) {
        const parsedData = JSON.parse(storedData);
        setLocation(parsedData.location || null);
        setPlaces(parsedData.places || []);
        setHotels(parsedData.hotels || []);
        setEateries(parsedData.eaterys || []);
        setLocalFoods(parsedData.localFoods || []);
      }
    };

    fetchData();
  }, [searchValue]);
  return (
    <div className="flex min-h-[calc(100svh-4rem)]">
      <div className="w-full mx-auto">
        <div className="mb-12">
          {location && <LocationInfoCard {...location} />}
          <div className="w-screen max-w-7xl mx-auto">
            <div className="flex flex-col">
              <h1 className="text-left text-xl font-bold mt-2">
                {t("placesTitle")}
              </h1>
              <PlacesCarousel data={places} loading={!loading} />
            </div>
            <div className="flex flex-col">
              <h1 className="text-left text-xl font-bold mt-2">
                {t("hotelsTitle")}
              </h1>
              <HotelsCarousel data={hotels} loading={!loading} />
            </div>
            <div className="flex flex-col">
              <h1 className="text-left text-xl font-bold mt-2">
                {t("eateryTitle")}
              </h1>
              <EateryCarousel data={eateries} loading={!loading} />
            </div>
            <div className="flex flex-col">
              <h1 className="text-left text-xl font-bold mt-2">
                {t("foodTitle")}
              </h1>
              <FoodsCarousel data={localFoods} loading={!loading} />
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
