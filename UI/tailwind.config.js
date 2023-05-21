/** @type {import('tailwindcss').Config} */
import daisyui from "daisyui";
export default {
  content: [
    "./components/**/*.{jsx,tsx}",
    "./routes/**/*.{jsx,tsx}",
    "./islands/**/*.{jsx,tsx}",
  ],
  theme: {
    extend: {
      backgroundImage: {
        "search": "url('/search.svg')",
        "logo": "url('/cg-logo-black.svg')",
      },
      backgroundSize: {
        "auto": "auto",
        "cover": "cover",
        "contain": "contain",
        "70%": "70%",
      },
    },
  },
  plugins: [daisyui],
};
