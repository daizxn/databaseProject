/// <reference types="vite/client" />

// 声明全局属性类型
declare module '@vue/runtime-core' {
  interface ComponentCustomProperties {
    $request: <T = unknown>(config: Record<string, unknown>) => Promise<T>
    $baseUrl: string
  }
}
