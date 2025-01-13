import React, { createContext, useContext, useReducer, ReactNode } from 'react';

// Định nghĩa các loại ngôn ngữ
export type Language = 'en' | 'vi';

// Dữ liệu ngôn ngữ
const translations = {
  en: {
    logo: 'ViVu Vietnam',
    input: 'Enter the location you want to go to',
    signIn: 'Sign in',
    platform: 'Platform',
    home: 'Home',
    history: 'History',
    favorite: 'Your favorites list',
    hotels: 'Hotels',
    foods: 'Foods & Drinks',
    footer: 'Copyright of this website belongs to Vo Thanh Tuyen',
  },
  vi: {
    logo: 'ViVu Việt Nam',
    input: 'Nhập địa điểm bạn muốn đến',
    signIn: 'Đăng nhập',
    platform: 'Nền tảng',
    home: 'Trang chủ',
    history: 'Lịch sử',
    favorite: 'Danh sách yêu thích của bạn',
    hotels: 'Khách sạn',
    foods: 'Thức ăn & Đồ uống',
    footer: 'Bản quyền của website này thuộc về Võ Thanh Tuyền',
  },
};

// Khởi tạo trạng thái mặc định
const initialState = {
  language: 'en' as Language,
  setLanguage: (lang: Language) => {},
  t: (key: string) => '',
};

// Tạo Context
const LanguageContext = createContext(initialState);

// Reducer để quản lý ngôn ngữ
const languageReducer = (state: any, action: { type: 'SET_LANGUAGE'; payload: Language }) => {
  switch (action.type) {
    case 'SET_LANGUAGE':
      return { ...state, language: action.payload };
    default:
      return state;
  }
};

// Provider
export const LanguageProvider: React.FC<{ children: ReactNode }> = ({ children }) => {
  const [state, dispatch] = useReducer(languageReducer, {
    language: initialState.language,
  });

  // Hàm để chuyển đổi ngôn ngữ
  const setLanguage = (lang: Language) => {
    dispatch({ type: 'SET_LANGUAGE', payload: lang });
  };

  // Hàm dịch
  const t = (key: string) => translations[state.language as Language][key] || key;

  return (
    <LanguageContext.Provider value={{ language: state.language, setLanguage, t }}>
      {children}
    </LanguageContext.Provider>
  );
};

// Hook để sử dụng LanguageContext
export const useLanguage = () => useContext(LanguageContext);
