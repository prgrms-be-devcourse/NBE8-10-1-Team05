export interface Item {
  id: number;
  category: string;
  name: string;
  price: number;
  imageUrl: string;
}

export interface PurchaseItem {
  itemId: number;
  category: string;
  quantity: number;
  name: string;
  price: number;
  imageUrl: string;
}