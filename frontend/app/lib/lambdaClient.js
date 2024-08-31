import { LambdaClient } from "@aws-sdk/client-lambda";

const lambdaClient = new LambdaClient({
    region: process.env.NEXT_PUBLIC_REGION,
    credentials: {
        accessKeyId: process.env.NEXT_PUBLIC_ACCESS_KEY_ID,
        secretAccessKey: process.env.NEXT_PUBLIC_SECRET_ACCESS_KEY,
      }
});
export { lambdaClient };
