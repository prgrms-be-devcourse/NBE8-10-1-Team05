import { PurchaseItem } from "./item";

export interface Order {
  id: number;
  email: string;
  address: string,
  zipCode: string;
  createDate: string;
  modifyDate: string;
  orderItem: PurchaseItem[];
  total: number;
}