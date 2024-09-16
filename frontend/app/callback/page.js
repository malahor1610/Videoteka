"use client";
import { useRouter } from "next/navigation";
import { useEffect } from "react";
import { exchangeCodeForToken } from "../lib/data";

export default function Callback() {
  const router = useRouter();

  useEffect(() => {
    const exchange = async () => {
      let res = await exchangeCodeForToken();
      if (res) {
        router.push("/");
      }
    };
    exchange();
  }, [router]);

  return <div>Przetwarzanie logowania...</div>;
}
