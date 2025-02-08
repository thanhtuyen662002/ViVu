import React, { createContext, useContext, useReducer, ReactNode } from "react";

// Định nghĩa các loại ngôn ngữ
export type Language = "en" | "vi";

// Dữ liệu ngôn ngữ
const translations = {
  en: {
    logo: "ViVu Vietnam",
    input: "Enter the location you want to go to",
    signIn: "Sign in",
    platform: "Platform",
    home: "Home",
    history: "History",
    favorite: "Your favorites list",
    hotels: "Hotels",
    foods: "Foods & Drinks",
    guideline:
      "To start, please enter the information about the location by clicking the search button on the top!",
    searchDialogTitle: "Search Location",
    searchDialogDesc:
      "Enter the information below and click the search button to wait a moment.",
    location: "Location",
    locationExp: "Example: Ha Noi, Viet Nam",
    locationMess: "Location is required",
    budget: "Budget",
    budgetExp: "Example: 1,000,000 VND or $100",
    demand: "Demand",
    demandExp: "Example: Tourist, Travel, ...",
    requirement: "Detailed requirement",
    requirementExp: `Example:
    - Hotel: Rustic, simple style, close to nature.
    - Food: Vegetarian, reasonable price.
    - Itinerary: Lasts 1 week, including time to rest and explore.`,
    searchButton: "Search",
    placeToVisit: "Place to visit",
    placeToVisitDesc: "Information about the place you want to go to.",
    placeToVisitName: "Location Name",
    placeToVisitDescription: "Description",
    placeToVisitNote: "Note",
    hotelsDesc: "Information about the hotels you can go to.",
    footer: "Copyright of this website belongs to Vo Thanh Tuyen",
    region: "Region:",
    country: "Country:",
    placesTitle: "Places to visit",
    hotelsTitle: "Hotels, resorts, homestays",
    eateryTitle: "Restaurants, Eateries",
    foodTitle: "Local Foods",
    rating: "Rating: ",
    type: "Type: ",
  },
  vi: {
    logo: "ViVu Việt Nam",
    input: "Nhập địa điểm bạn muốn đến",
    signIn: "Đăng nhập",
    platform: "Nền tảng",
    home: "Trang chủ",
    history: "Lịch sử",
    favorite: "Danh sách yêu thích của bạn",
    hotels: "Khách sạn",
    foods: "Thức ăn & Đồ uống",
    guideline:
      "Để bắt đầu bạn hãy nhập các thông tin về địa điểm bằng cách ấn vào nút tìm kiếm phía trên!",
    searchDialogTitle: "Tìm kiếm địa điểm",
    searchDialogDesc:
      "Nhập các thông tin bên dưới sau đó ấn tìm kiếm và đợi giây lát.",
    location: "Địa điểm",
    locationExp: "Ví dụ: Hà Nội, Việt Nam",
    locationMess: "Vui lòng nhập địa điểm",
    budget: "Ngân sách",
    budgetExp: "Ví dụ: 1,000,000 VND hoặc $100",
    demand: "Nhu cầu",
    demandExp: "Ví dụ: Nghỉ dưỡng, du lịch, ...",
    requirement: "Yêu cầu chi tiết",
    requirementExp: `Ví dụ:
    - Khách sạn: Phong cách mộc mạc, giản dị, gần thiên nhiên.
    - Món ăn: Đồ chay, giá cả hợp lý.
    - Lịch trình: Kéo dài 1 tuần, bao gồm cả thời gian nghỉ ngơi và khám phá.`,
    searchButton: "Tìm kiếm",
    placeToVisit: "Địa điểm tham quan",
    placeToVisitDesc: "Thông tin về địa điểm mà bạn đang muốn đến.",
    placeToVisitName: "Tên địa điểm",
    placeToVisitDescription: "Mô tả",
    placeToVisitNote: "Ghi chú",
    hotelsDesc: " Thông tin về khách sạn mà bạn có thể muốn đến.",
    footer: "Bản quyền của website này thuộc về Võ Thanh Tuyền",
    region: "Khu vực:",
    country: "Đất nước:",
    placesTitle: "Các địa điểm tham quan",
    hotelsTitle: "Các khách sạn, resort, homestay",
    eateryTitle: "Các nhà hàng, quán ăn",
    foodTitle: "Các món ăn",
    rating: "Đánh giá: ",
    type: "Loại: "
  },
};

// Khởi tạo trạng thái mặc định
const initialState = {
  language: "vi" as Language,
  setLanguage: (lang: Language) => {},
  t: (key: string) => "",
};

// Tạo Context
const LanguageContext = createContext(initialState);

// Reducer để quản lý ngôn ngữ
const languageReducer = (
  state: any,
  action: { type: "SET_LANGUAGE"; payload: Language }
) => {
  switch (action.type) {
    case "SET_LANGUAGE":
      return { ...state, language: action.payload };
    default:
      return state;
  }
};

// Provider
export const LanguageProvider: React.FC<{ children: ReactNode }> = ({
  children,
}) => {
  const [state, dispatch] = useReducer(languageReducer, {
    language: initialState.language,
  });

  // Hàm để chuyển đổi ngôn ngữ
  const setLanguage = (lang: Language) => {
    dispatch({ type: "SET_LANGUAGE", payload: lang });
  };

  // Hàm dịch
  const t = (key: string) =>
    translations[state.language as Language][key] || key;

  return (
    <LanguageContext.Provider
      value={{ language: state.language, setLanguage, t }}
    >
      {children}
    </LanguageContext.Provider>
  );
};

// Hook để sử dụng LanguageContext
export const useLanguage = () => useContext(LanguageContext);
