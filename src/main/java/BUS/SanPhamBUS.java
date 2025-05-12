/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS;

/**
 *
 * @author lespa
 */
import DAO.SanPhamDAO;
import DTO.SanPhamDTO;
import java.util.ArrayList;
public class SanPhamBUS 
{
     private ArrayList<SanPhamDTO> dssp;
    public SanPhamBUS(int i1)
    {
        listSP();
    }
    public SanPhamBUS(){
    }
    
    // Hàm hiện tại
    public SanPhamDTO get(String MaSP)
    {
        for(SanPhamDTO sp : dssp )
        {
            if(sp.getMaXe().equals(MaSP))
            {
                return sp;
            }
        }
        return null;
    }
    
    // Thêm hàm mới với tên rõ ràng hơn
    public SanPhamDTO getSanPhamById(String MaXe) {
        for(SanPhamDTO sp : dssp) {
            if(sp.getMaXe().equals(MaXe)) {
                return sp;
            }
        }
        return null;
    }
    
    public void listSP()
    {
        SanPhamDAO spDAO = new SanPhamDAO();
        dssp = new ArrayList<>();
        dssp = spDAO.list();
    }
    public void addSP(SanPhamDTO sp)
    {
        dssp.add(sp);
        SanPhamDAO spDAO = new SanPhamDAO();
        spDAO.add(sp);
    }

    public void deleteSP(String MaSP)
    {
        for(SanPhamDTO sp : dssp )
        {
            if(sp.getMaXe().equals(MaSP))
            {
                dssp.remove(sp);
                SanPhamDAO spDAO = new SanPhamDAO();
                spDAO.delete(MaSP);
                return;
            }
        }
    }
    public void setSP(SanPhamDTO s)
    {
        for(int i = 0 ; i < dssp.size() ; i++)
        {
            if(dssp.get(i).getMaXe().equals(s.getMaXe()))
            {
                dssp.set(i, s);
                SanPhamDAO spDAO = new SanPhamDAO();
                spDAO.set(s);
                return;
            }
        }
    }
    public boolean check(String masp)
    {
        for(SanPhamDTO sp : dssp)
        {
            if(sp.getMaXe().equals(masp))
            {
                return true;
            }
        }
        return false;
    }
    public ArrayList<SanPhamDTO> getList() {
        return dssp;
    }
}