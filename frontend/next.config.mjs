/** @type {import('next').NextConfig} */
const nextConfig = {
  async redirects() {
    return [
      {
        source: '/',
        destination: '/watchlist',
        permanent: true,
      },
    ]
  },
};

export default nextConfig;
