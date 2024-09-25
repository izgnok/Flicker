// SignInPage.tsx
import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { signin } from "../../apis/authApi";
import { AxiosError } from "axios";
import UsAndThem from "../../assets/background/UsAndThem.png";
import Cookies from "js-cookie";
import { IoMdCheckboxOutline, IoMdSquareOutline } from "react-icons/io";

const SignInPage: React.FC = () => {
  const [formData, setFormData] = useState({
    userId: "",
    password: "",
    rememberMe: false,
  });

  const [error, setError] = useState("");
  const navigate = useNavigate();

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
    setError(""); // 입력 시 오류 메시지 초기화
  };

  const toggleRememberMe = () => {
    setFormData((prevData) => ({
      ...prevData,
      rememberMe: !prevData.rememberMe,
    }));
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    try {
      const response = await signin({
        userId: formData.userId,
        password: formData.password,
      });

      if (response) {
        const { accessToken, refreshToken } = response;

        if (accessToken) {
          localStorage.setItem("accessToken", accessToken);
          Cookies.set("refreshToken", refreshToken, { expires: 7 });

          alert("로그인 성공! \nFlicker에서 반짝이는 순간을 기록하세요.");
          navigate("/"); // 메인 페이지로 이동
        } else {
          throw new Error("토큰이 반환되지 않았습니다.");
        }
      } else {
        throw new Error("로그인 응답이 없습니다.");
      }
    } catch (error: unknown) {
      const err = error as AxiosError;

      console.error("로그인 오류:", err);

      if (err.response && err.response.status === 400) {
        setError("로그인 실패: 아이디나 비밀번호가 올바르지 않습니다.");
      } else {
        setError(
          "로그인 실패: 서버에 문제가 발생했습니다. 나중에 다시 시도하세요."
        );
      }
    }
  };

  const goToPasswordReset = () => {
    navigate("/passwordreset"); // '/passwordreset' 경로로 이동
  };

  return (
    <div
      className="min-h-screen w-screen bg-black flex items-center justify-center bg-cover bg-center"
      style={{ backgroundImage: `url(${UsAndThem})` }}
    >
      <form onSubmit={handleSubmit} className="w-full max-w-xl p-8">
        <h2 className="text-3xl font-bold mb-6 text-white text-center">
          로그인
        </h2>

        <input
          type="text"
          name="userId"
          placeholder="아이디"
          value={formData.userId}
          onChange={handleChange}
          className="w-full py-2 px-4 mb-4 border border-gray-500 rounded bg-black text-white placeholder-gray-400 focus:outline-none"
        />

        <input
          type="password"
          name="password"
          placeholder="비밀번호"
          value={formData.password}
          onChange={handleChange}
          className="w-full py-2 px-4 mb-4 border border-gray-500 rounded bg-black text-white placeholder-gray-400 focus:outline-none"
        />

        {error && <p className="text-red-500 text-xs mb-2">{error}</p>}

        <div className="flex items-center justify-between w-full mb-10">
          <label
            className="flex items-center text-gray-100 text-sm cursor-pointer"
            onClick={toggleRememberMe}
          >
            {formData.rememberMe ? (
              <IoMdCheckboxOutline className="mr-2 ml-2 text-xl text-gray-100" />
            ) : (
              <IoMdSquareOutline className="mr-2 ml-2 text-xl text-gray-100" />
            )}
            자동 로그인
          </label>
          <a
            type="button"
            className="text-sm text-gray-100 underline hover:underline mr-2"
            onClick={goToPasswordReset}
          >
            비밀번호 재설정
          </a>
        </div>

        <button
          type="submit"
          className="w-full py-2 mb-4 text-white bg-[#4D7FFF] rounded hover:bg-blue-600 focus:outline-none"
        >
          로그인
        </button>

        <p className="text-sm text-center text-gray-200">
          Flicker와 함께 반짝이는 순간을 기록하시겠어요?&nbsp;&nbsp;&nbsp;
          <a href="/signup" className="text-gray-100 underline hover:underline">
            회원가입
          </a>
        </p>
      </form>
    </div>
  );
};

export default SignInPage;
