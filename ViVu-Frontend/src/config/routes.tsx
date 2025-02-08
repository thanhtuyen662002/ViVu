import { Routes, Route } from "react-router-dom";
import ResultsPage from "@/page/ResultsPage";
import StartPage from "@/page/StartPage";

const AppRoutes = () => {
  return (
    <Routes>
      <Route path="/" element={<StartPage />} />
      <Route path="/results" element={<ResultsPage />} />
    </Routes>
  );
};

export default AppRoutes;
